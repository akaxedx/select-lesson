package anda.selectlesson.controller;

import anda.selectlesson.req.teacherReq.SetLessonReq;
import anda.selectlesson.req.userReq.RegisterReq;
import anda.selectlesson.service.teacher.TeacherService;
import anda.selectlesson.service.user.UserService;
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
@RequestMapping("/teacher")
@Tag(name = "老师请求")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Operation(summary = "创建课程")
    @PostMapping("/set-lesson")
    public Response<Long> register(@RequestBody SetLessonReq req) throws IOException {
        if(null == req.getLessonName()) {
            return Response.error("参数缺失: 课程名称");
        }
        if(null == req.getTime()) {
            return Response.error("参数缺失: 课程时间");
        }
        if(req.getTime() %2 != 0) {
            return Response.error("参数错误: 课程时间");
        }
        return teacherService.setLesson(req);
    }
}
