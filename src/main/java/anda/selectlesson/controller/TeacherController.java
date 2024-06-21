package anda.selectlesson.controller;

import anda.selectlesson.model.dto.PageLessonDTO;
import anda.selectlesson.model.dto.StudentsDTO;
import anda.selectlesson.model.po.Room;
import anda.selectlesson.repo.RoomRepo;
import anda.selectlesson.req.BaseReq;
import anda.selectlesson.req.teacherReq.GetMyLessonStudentReq;
import anda.selectlesson.req.teacherReq.SetLessonReq;
import anda.selectlesson.req.teacherReq.RegisterStudentReq;
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
import java.util.List;

@RestController
@RequestMapping("/teacher")
@Tag(name = "老师请求")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserService userService;
    @Autowired
    RoomRepo roomRepo;
    @Operation(summary = "创建课程")
    @PostMapping("/set-lesson")
    public Response<Long> register(@RequestBody SetLessonReq req) throws IOException {
        if(null == req.getLessonName() || req.getLessonName().isBlank()) {
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
        if (req.getStartDate() == null || req.getEndDate() == null) {
            return Response.error("请确认起始时间");
        }
        if (req.getStartDate().after(req.getEndDate())) {
            return Response.error("开始日期不能在结束日期后");
        }

        return teacherService.setLesson(req);
    }

    @Operation(summary = "注册学生身份")
    @PostMapping("/student")
    public Response<Long> registerStudent(@RequestBody RegisterStudentReq req) {
        try {
            if (null == req.getStudentNum()) {
                req.setStudentNum(80);
            }
            Long l = userService.registerStudent(req);
            if (l == -1L) {
                return Response.error("登录异常，请重新登录");
            }
            return Response.ok(l);
        }catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    @Operation(summary = "获得可用教室")
    @PostMapping("/get-ok-room")
    public Response<List<Room>> getAllOkRoom(@RequestBody BaseReq req) {
        List<Room> rooms = roomRepo.getRoomsByUsedIsNull();
        return Response.ok(rooms);
    }

    @Operation(summary = "获得我的课程")
    @PostMapping("/get-my-lessons")
    public Response<PageLessonDTO> getTMyLessons(@RequestBody BaseReq req) throws IOException {
        return teacherService.getMyLessons(req);
    }

    @Operation(summary = "获取我课程的学生")
    @PostMapping("/get-my-lessons-students")
    public Response<List<StudentsDTO>> getMyLessonsStudents(@RequestBody GetMyLessonStudentReq req) throws IOException {
        return teacherService.getMyLessonsStudents(req);
    }
}
