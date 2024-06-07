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
import java.util.ArrayList;

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
        if(null == req.getRoomId()) {
            return Response.error("参数缺失: 上课地点");
        }
        if (req.getMonday()==null) {
            req.setMonday(new ArrayList<>());
        }
        if (req.getTuesday()==null) {
            req.setThursday(new ArrayList<>());
        }
        if (req.getWednesday()==null) {
            req.setWednesday(new ArrayList<>());
        }
        if (req.getThursday()==null) {
            req.setThursday(new ArrayList<>());
        }
        if (req.getFriday()==null) {
            req.setFriday(new ArrayList<>());
        }
        if (req.getSaturday()==null) {
            req.setSaturday(new ArrayList<>());
        }
        if (req.getSunday()==null) {
            req.setSunday(new ArrayList<>());
        }

        return teacherService.setLesson(req);
    }
}
