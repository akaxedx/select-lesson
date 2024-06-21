package anda.selectlesson.service.teacher;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.model.dto.LessonDTO;
import anda.selectlesson.model.dto.PageLessonDTO;
import anda.selectlesson.model.dto.StudentsDTO;
import anda.selectlesson.model.dto.UsedTimeDTO;
import anda.selectlesson.model.po.*;
import anda.selectlesson.repo.*;
import anda.selectlesson.req.BaseReq;
import anda.selectlesson.req.teacherReq.GetMyLessonStudentReq;
import anda.selectlesson.req.teacherReq.SetLessonReq;
import anda.selectlesson.service.lesson.TimeService;
import anda.selectlesson.system.Response;
import anda.selectlesson.utils.JwtTokenUtils;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeacherService {
    @Autowired
    LessonsRepo lessonsRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    TimeService timeService;
    @Autowired
    RoomRepo roomRepo;
    private final static int MAX_NUM = 50;

    public Response<Long> setLesson(SetLessonReq req) throws IOException {
        Long currentUserId = JwtTokenUtils.getCurrentUserId();
        User currentUser = userRepo.getUserById(currentUserId);
        if (!currentUser.getAuthority().equals(AuthorityType.TEACHER)) {
            return Response.error("当前用户不是老师");
        }
        Teacher currentTeacher = teacherRepo.getByUserId(currentUserId);
        List<Long> lessonIds = JSONUtil.toList(currentTeacher.getLessonIds(), Long.class);
        Lesson exitLesson = lessonsRepo.getByTeacherIdAndLessonName(currentTeacher.getId(), req.getLessonName());
        Room room = roomRepo.getReferenceById(req.getRoomId());
        if (null != exitLesson) {
            return Response.error("该课程已被创建");
        }
        if (room.getUsed() != null && room.getUsed()) {
            return Response.error("教室已被占用");
        }
        Lesson lesson = new Lesson();
        lesson.setTeacherId(currentTeacher.getId());
        lesson.setRoomId(req.getRoomId());
        lesson.setPos(room.getBuilding()+":"+room.getFloor()+"楼"+room.getNum()+"号教室");
        room.setUsed(true);
        // time
        UsedTimeDTO usedTimeDTO = getLessonTimeDTO(req);
        lesson.setTime(JSONUtil.toJsonStr(usedTimeDTO));

        // date
        lesson.setStartDate(req.getStartDate());
        lesson.setEndDate(req.getEndDate());

        // maxNum
        lesson.setMaxNum(MAX_NUM);

        // name
        lesson.setLessonName(req.getLessonName());

        // currentNum
        lesson.setCurrentNum(0);

        // studentIds
        lesson.setStudentIds("[]");

        // 课程冲突
        Long isConflict = timeService.resolveConflict(lessonIds, lesson);
        if(isConflict != -1L) {
            Lesson conflictLesson = lessonsRepo.getReferenceById(isConflict);
            return Response.error("课程冲突: " + conflictLesson.getLessonName());
        }
        roomRepo.save(room);
        Lesson savedLesson = lessonsRepo.save(lesson);

        lessonIds.add(savedLesson.getId());
        currentTeacher.setLessonIds(JSONUtil.toJsonStr(lessonIds));
        teacherRepo.save(currentTeacher);
        return Response.ok(savedLesson.getId());
    }

    public Response<PageLessonDTO> getMyLessons(BaseReq req) throws IOException {
        Long userId = JwtTokenUtils.getCurrentUserId();
        Teacher teacher = teacherRepo.getByUserId(userId);
        String lessonIdsJson = teacher.getLessonIds();
        List<Long> lessonIds = JSONUtil.toList(lessonIdsJson, Long.class);
        List<Lesson> lessons = lessonsRepo.getLessonsByIdIsIn(lessonIds);
        PageLessonDTO pageLessonDTO = new PageLessonDTO();
        List<LessonDTO> lessonDTOS = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDTOS.add(lessonPO2DTO(lesson));
        }
        pageLessonDTO.setLessonDTOS(lessonDTOS);
        return Response.ok(pageLessonDTO);
    }

    public Response<List<StudentsDTO>> getMyLessonsStudents(GetMyLessonStudentReq req) throws IOException {
        Long userId = JwtTokenUtils.getCurrentUserId();
        Teacher teacher = teacherRepo.getByUserId(userId);
        String lessonIdsJson = teacher.getLessonIds();
        List<Long> lessonIds = JSONUtil.toList(lessonIdsJson, Long.class);
        boolean contains = lessonIds.contains(req.getLessonId());
        if (!contains) {
            return Response.error("请求错误");
        }
        Lesson lesson = lessonsRepo.getReferenceById(req.getLessonId());
        String studentIds = lesson.getStudentIds();
        List<Long> students = JSONUtil.toList(studentIds, Long.class);
        List<Student> studentList = studentRepo.getStudentsByIdIsIn(students);
        List<StudentsDTO> studentsDTOList = new ArrayList<>();
        for (Student student : studentList) {
            studentsDTOList.add(studentPO2DTO(student));
        }
        return Response.ok(studentsDTOList);
    }

    private static UsedTimeDTO getLessonTimeDTO(SetLessonReq req) {
        int lessonTime = 0;
        UsedTimeDTO usedTimeDTO = new UsedTimeDTO();
        // 设置课时
        usedTimeDTO.setMonday(req.getMonday());
        lessonTime += req.getMonday().size();
        usedTimeDTO.setTuesday(req.getTuesday());
        lessonTime += req.getTuesday().size();
        usedTimeDTO.setWednesday(req.getWednesday());
        lessonTime += req.getWednesday().size();
        usedTimeDTO.setThursday(req.getThursday());
        lessonTime += req.getThursday().size();
        usedTimeDTO.setFriday(req.getFriday());
        lessonTime += req.getFriday().size();
        usedTimeDTO.setSaturday(req.getSaturday());
        lessonTime += req.getSaturday().size();
        usedTimeDTO.setSunday(req.getSunday());
        lessonTime += req.getSunday().size();
        usedTimeDTO.setLessonTime(lessonTime);
        return usedTimeDTO;
    }

    private LessonDTO lessonPO2DTO(Lesson lesson) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setUsername(lessonDTO.getUsername());
        lessonDTO.setLesson(lesson);
        return lessonDTO;
    }

    private StudentsDTO studentPO2DTO(Student student) {
        StudentsDTO studentsDTO = new StudentsDTO();
        studentsDTO.setStudentId(student.getId());
        User user = userRepo.getUserById(student.getUserId());
        studentsDTO.setName(user.getUsername());
        return studentsDTO;
    }

}
