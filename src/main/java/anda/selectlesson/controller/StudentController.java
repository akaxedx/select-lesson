package anda.selectlesson.controller;

import anda.selectlesson.req.studentReq.SelectLessonReq;
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

@RestController
@RequestMapping("/student")
@Tag(name = "学生请求")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Operation(summary = "选课")
    @PostMapping("/select-lesson")
    public Response<Long> selectLesson(@RequestBody SelectLessonReq req) throws IOException {
        if(req.getLessonName() == null) {
            return Response.error("课程名为空");
        }
        if(req.getTeacherId() == null) {
            return Response.error("老师id为空");
        }
        return studentService.selectLesson(req);
    }

}
