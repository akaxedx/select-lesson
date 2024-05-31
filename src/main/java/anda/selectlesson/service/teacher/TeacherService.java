package anda.selectlesson.service.teacher;

import anda.selectlesson.config.JwtInterceptor;
import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.model.po.Lesson;
import anda.selectlesson.model.po.Teacher;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.LessonsRepo;
import anda.selectlesson.repo.StudentRepo;
import anda.selectlesson.repo.TeacherRepo;
import anda.selectlesson.repo.UserRepo;
import anda.selectlesson.req.teacherReq.SetLessonReq;
import anda.selectlesson.system.Response;
import anda.selectlesson.utils.JwtTokenUtils;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    private final static int MAX_NUM = 50;
    public Response<Long> setLesson(SetLessonReq req) throws IOException {
        Long currentUserId = JwtTokenUtils.getCurrentUserId();
        User currentUser = userRepo.getUserById(currentUserId);
        if(!currentUser.getAuthority().equals(AuthorityType.TEACHER)) {
            return Response.error("当前用户不是老师");
        }
        Teacher currentTeacher = teacherRepo.getByUserId(currentUserId);
        List<Long> lessonIds = JSONUtil.toList(currentTeacher.getLessonIds(), Long.class);
        Lesson exitLesson = lessonsRepo.getByTeacherIdAndLessonName(currentTeacher.getId(), req.getLessonName());
        if(null != exitLesson) {
            return Response.error("该课程已被创建");
        }
        Lesson lesson = new Lesson();
        lesson.setTeacherId(currentTeacher.getId());
        lesson.setTime(req.getTime());
        lesson.setMaxNum(MAX_NUM);
        lesson.setLessonName(req.getLessonName());
        lesson.setCurrentNum(0);
        lesson.setStudentIds("[]");
        Lesson savedLesson = lessonsRepo.save(lesson);
        lessonIds.add(savedLesson.getId());
        currentTeacher.setLessonIds(JSONUtil.toJsonStr(lessonIds));
        teacherRepo.save(currentTeacher);
        return Response.ok(savedLesson.getId());
    }




}
