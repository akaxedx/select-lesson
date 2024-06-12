package anda.selectlesson.controller;

import anda.selectlesson.model.dto.LessonDTO;
import anda.selectlesson.model.dto.MyLessonDTO;
import anda.selectlesson.model.dto.PageLessonDTO;
import anda.selectlesson.model.dto.PageTeacherDTO;
import anda.selectlesson.req.BaseReq;
import anda.selectlesson.req.studentReq.GetAllLessonsReq;
import anda.selectlesson.req.studentReq.GetAllTeachersReq;
import anda.selectlesson.req.studentReq.SelectLessonReq;
import anda.selectlesson.req.teacherReq.GetLessonReq;
import anda.selectlesson.service.student.StudentService;
import anda.selectlesson.system.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "学生请求")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Operation(summary = "选课")
    @PostMapping("/select-lesson")
    public Response<Long> selectLesson(@RequestBody SelectLessonReq req) throws IOException {
        return studentService.selectLesson(req);
    }

    @Operation(summary = "获取课程")
    @PostMapping("/get-my-lessons")
    public Response<List<MyLessonDTO>> getMyLessons(@RequestBody GetLessonReq req) throws IOException {
        return studentService.getMyLessons(req);
    }

    @Operation(summary = "所有课程列表")
    @PostMapping("/get-all-lessons")
    public Response<PageLessonDTO> getAllLessons(@RequestBody GetAllLessonsReq req) throws IOException {
        return studentService.getAllLessons(req);
    }
    @Operation(summary = "退课")
    @PostMapping("/go-away-lesson")
    public Response<Boolean> goAwayLesson(@RequestBody SelectLessonReq req) throws IOException {
        return studentService.goAwayLesson(req);
    }
    @Operation(summary = "所有老师列表")
    @PostMapping("/get-all-teachers")
    public Response<PageTeacherDTO> getAllTeachers(@RequestBody GetAllTeachersReq req) throws IOException {
        return studentService.getAllTeachers(req);
    }
}
