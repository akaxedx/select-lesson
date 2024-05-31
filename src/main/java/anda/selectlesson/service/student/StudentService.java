package anda.selectlesson.service.student;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.model.po.Lesson;
import anda.selectlesson.model.po.Student;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.LessonsRepo;
import anda.selectlesson.repo.StudentRepo;
import anda.selectlesson.repo.UserRepo;
import anda.selectlesson.req.studentReq.SelectLessonReq;
import anda.selectlesson.system.Response;
import anda.selectlesson.utils.JwtTokenUtils;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class StudentService {
    @Autowired
    LessonsRepo lessonsRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    StudentRepo studentRepo;
    public Response<Long> selectLesson(SelectLessonReq req) throws IOException {
        Lesson selectLesson = lessonsRepo.getByTeacherIdAndLessonName(req.getTeacherId(), req.getLessonName());
        if (selectLesson == null) {
            return Response.error("没有该课程");
        }
        User currentUser = userRepo.getUserById(JwtTokenUtils.getCurrentUserId());
        if(!currentUser.getAuthority().equals(AuthorityType.STUDENT)) {
            return Response.error("当前用户不是学生");
        }
        Student student = studentRepo.getByUserId(currentUser.getId());
        List<Long> lessonIds = new ArrayList<>();
        if(student.getLessonIds() != null) {
            lessonIds = JSONUtil.toList(student.getLessonIds(), Long.class);
        }
        List<Lesson> exitLessons = lessonsRepo.getLessonsByIdIsIn(lessonIds);
        for (Lesson exitLesson : exitLessons) {
            if(Objects.equals(selectLesson.getTime(), exitLesson.getTime())) {
                return Response.error("课程时间冲突，冲突课程: " + exitLesson.getLessonName());
            }
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
}
