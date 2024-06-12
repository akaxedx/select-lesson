package anda.selectlesson.service.student;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.model.dto.*;
import anda.selectlesson.model.po.Lesson;
import anda.selectlesson.model.po.Student;
import anda.selectlesson.model.po.Teacher;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.*;
import anda.selectlesson.req.BaseReq;
import anda.selectlesson.req.studentReq.GetAllLessonsReq;
import anda.selectlesson.req.studentReq.GetAllTeachersReq;
import anda.selectlesson.req.studentReq.SelectLessonReq;
import anda.selectlesson.req.teacherReq.GetLessonReq;
import anda.selectlesson.service.lesson.TimeService;
import anda.selectlesson.system.Response;
import anda.selectlesson.utils.JwtTokenUtils;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class StudentService {
    @Autowired
    TimeService timeService;
    @Autowired
    LessonsRepo lessonsRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    public StudentRepo studentRepo;
    @Autowired
    LessonPageRepo lessonPageRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    UserPageRepo userPageRepo;
    public Response<Long> selectLesson(SelectLessonReq req) throws IOException {
        Lesson selectLesson = lessonsRepo.getReferenceById(req.getLessonId());
        User currentUser = userRepo.getUserById(JwtTokenUtils.getCurrentUserId());
        if(!currentUser.getAuthority().equals(AuthorityType.STUDENT)) {
            return Response.error("当前用户不是学生");
        }
        Student student = studentRepo.getByUserId(currentUser.getId());
        if (student == null) {
            return Response.error("请注册学生身份");
        }
        List<Long> lessonIds = new ArrayList<>();
        if(student.getLessonIds() != null) {
            lessonIds = JSONUtil.toList(student.getLessonIds(), Long.class);
        }

        Long isConflict = timeService.resolveConflict(lessonIds, selectLesson);
        if(isConflict != -1L) {
            Lesson conflictLesson = lessonsRepo.getReferenceById(isConflict);
            return Response.error("课程冲突: " + conflictLesson.getLessonName());
        }


        // 学生课程 id
        lessonIds.add(selectLesson.getId());
        student.setLessonIds(JSONUtil.toJsonStr(lessonIds));

        // 课程学生 id
        List<Long> studentIds = JSONUtil.toList(selectLesson.getStudentIds(), Long.class);
        studentIds.add(student.getId());
        selectLesson.setStudentIds(JSONUtil.toJsonStr(studentIds));

        selectLesson.setCurrentNum(selectLesson.getCurrentNum() + 1);
        studentRepo.save(student);
        lessonsRepo.save(selectLesson);
        return Response.ok(selectLesson.getId());
    }
    public Response<List<MyLessonDTO>> getMyLessons(GetLessonReq req) throws IOException {
        Long userId = JwtTokenUtils.getCurrentUserId();
        User user = userRepo.getUserById(userId);
        Student student = studentRepo.getByUserId(userId);
        Teacher teacher = teacherRepo.getByUserId(userId);
        if (student == null && teacher ==null) {
            if (user.getAuthority().equals(AuthorityType.STUDENT)) {
                return Response.error("请注册学生身份");
            }
        }
        String lessonIdsString = null;
        if (user.getAuthority().equals(AuthorityType.STUDENT)) {
            lessonIdsString = student.getLessonIds();
        }
        if (user.getAuthority().equals(AuthorityType.TEACHER)) {
            lessonIdsString = teacher.getLessonIds();
        }
        List<Long> lessonIds = JSONUtil.toList(lessonIdsString, Long.class);
        List<Lesson> lessons = lessonsRepo.getLessonsByIdIsIn(lessonIds);
        List<MyLessonDTO> myLessonDTOS = new ArrayList<>();
        for (Lesson lesson : lessons) {
            MyLessonDTO myLessonDTO = new MyLessonDTO();
            UsedTimeDTO usedTimeDTO = JSONUtil.toBean(lesson.getTime(), UsedTimeDTO.class);
            myLessonDTO.setLessonName(lesson.getLessonName());
            myLessonDTO.setUsedTimeDTO(usedTimeDTO);
            myLessonDTOS.add(myLessonDTO);
        }

        return Response.ok(myLessonDTOS);
    }
    public Response<PageLessonDTO> getAllLessons(GetAllLessonsReq req) throws IOException {
        if (req.getLessonName() == null) {
            req.setLessonName("");
        }
        Long userId = JwtTokenUtils.getCurrentUserId();
        Student student = studentRepo.getByUserId(userId);
        List<Long> lessonIds = new ArrayList<>();
        if (student.getLessonIds() != null) {
           lessonIds = JSONUtil.toList(student.getLessonIds(), Long.class);
        }
        Pageable pageable = PageRequest.of(req.getPageNum() - 1, 10, Sort.by("id"));
        Page<Lesson> lessonsByLessonNameLike = lessonPageRepo.getLessonsByLessonNameLike("%"+req.getLessonName()+"%", pageable);
        List<Lesson> lessons = lessonsByLessonNameLike.stream().toList();
        List<LessonDTO> lessonDTOList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            LessonDTO lessonDTO = new LessonDTO();
            lessonDTO.setLesson(lesson);
            lessonDTO.setIsChoose(false);
            for (Long lessonId : lessonIds) {
                if (Objects.equals(lesson.getId(), lessonId)) {
                    lessonDTO.setIsChoose(true);
                }
            }
            Teacher teacher = teacherRepo.getReferenceById(lesson.getTeacherId());
            User user = userRepo.getReferenceById(teacher.getUserId());
            lessonDTO.setUsername(user.getUsername());
            lessonDTOList.add(lessonDTO);
        }
        PageLessonDTO pageLessonDTO = new PageLessonDTO();
        pageLessonDTO.setLessonDTOS(lessonDTOList);
        pageLessonDTO.setPageTotal(lessonsByLessonNameLike.getTotalPages());
        return Response.ok(pageLessonDTO);
    }
    public Response<Boolean> goAwayLesson(SelectLessonReq req) throws IOException {
        Lesson lesson = lessonsRepo.getReferenceById(req.getLessonId());
        List<Long> studentIds = JSONUtil.toList(lesson.getStudentIds(), Long.class);
        Long userId = JwtTokenUtils.getCurrentUserId();
        if (userId == null) {
            return Response.error("请重新登录");
        }
        Student student = studentRepo.getByUserId(userId);
        Long studentId = student.getId();
        studentIds.remove(studentId);
        lesson.setStudentIds(JSONUtil.toJsonStr(studentIds));
        lesson.setCurrentNum(lesson.getCurrentNum() - 1);
        lessonsRepo.save(lesson);
        List<Long> lessonIds = JSONUtil.toList(student.getLessonIds(), Long.class);
        lessonIds.remove(req.getLessonId());
        student.setLessonIds(JSONUtil.toJsonStr(lessonIds));
        studentRepo.save(student);
        return Response.ok(true);
    }
    public Response<PageTeacherDTO> getAllTeachers(GetAllTeachersReq req) throws IOException {

        Long userId = JwtTokenUtils.getCurrentUserId();
        Student student = studentRepo.getByUserId(userId);
        boolean isStudent = student != null;
        Pageable pageable = PageRequest.of(req.getPageNum() - 1, 10, Sort.by("id"));
        Page<User> users = userPageRepo.getUsersByAuthority(AuthorityType.TEACHER, pageable);
        List<Long> ids = new ArrayList<>();
        Map<Long, String> user2teacher = new HashMap<>();
        for (User user : users.getContent()) {
            ids.add(user.getId());
            user2teacher.put(user.getId(), user.getUsername());
        }
        List<Teacher> teachers = teacherRepo.getByUserIdIn(ids);
        PageTeacherDTO pageTeacherDTO = new PageTeacherDTO();
        List<TeacherDTO> teacherDTOS = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setIsChoose(false);
            if (isStudent) {
                List<StudentIdsDTO> studentIds = JSONUtil.toList(teacher.getStudentIds(), StudentIdsDTO.class);
                for (StudentIdsDTO studentId : studentIds) {
                    if (studentId.getExit() && Objects.equals(studentId.getStudentId(), student.getId())) {
                        teacherDTO.setIsChoose(true);
                    }
                }
            }
            teacherDTO.setTeacher(teacher);
            teacherDTO.setUsername(user2teacher.get(teacher.getUserId()));
            teacherDTOS.add(teacherDTO);
        }
        pageTeacherDTO.setTeacherDTOS(teacherDTOS);
        pageTeacherDTO.setPageTotal(pageTeacherDTO.getPageTotal());
        return Response.ok(pageTeacherDTO);
    }
}
