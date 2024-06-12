package anda.selectlesson.controller;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.model.dto.PageUserDTO;
import anda.selectlesson.model.po.Teacher;
import anda.selectlesson.model.po.User;
import anda.selectlesson.req.BaseReq;
import anda.selectlesson.req.managerReq.BuildBuildingReq;
import anda.selectlesson.req.managerReq.EditTeacherReq;
import anda.selectlesson.req.studentReq.GetAllLessonsReq;
import anda.selectlesson.req.studentReq.GetAllTeachersReq;
import anda.selectlesson.service.lesson.TimeService;
import anda.selectlesson.service.manager.ManagerService;
import anda.selectlesson.service.student.StudentService;
import anda.selectlesson.service.teacher.TeacherService;
import anda.selectlesson.service.user.UserService;
import anda.selectlesson.system.Response;
import anda.selectlesson.utils.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理员")
public class ManagerController {
    @Autowired
    ManagerService managerService;
    @Autowired
    TimeService timeService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserService userService;

    @Operation(summary = "设置老师")
    @PostMapping("/edit-teacher")
    public Response<Teacher> editTeacher(@RequestBody EditTeacherReq req) throws IOException {
        if (!userIsManager(req)) {
            return Response.error("当前用户不是管理员");
        }
        return managerService.editTeacher(req);
    }

    @Operation(summary = "设置教学楼")
    @PostMapping("/build-building")
    public Response<Boolean> buildBuilding (@RequestBody BuildBuildingReq req) throws IOException {
        if (!userIsManager(req)) {
            return Response.error("当前用户不是管理员");
        }
        return managerService.buildBuilding(req);
    }
    @Operation(summary = "获取所有用户")
    @PostMapping("/get-all-users")
    public Response<PageUserDTO> getAllUsers (@RequestBody GetAllTeachersReq req) throws IOException {
        if (!userIsManager(req)) {
            return Response.error("当前用户不是管理员");
        }
        return managerService.getAllUsers(req);
    }
    private boolean userIsManager(BaseReq req) throws IOException {
        Long userId = JwtTokenUtils.getCurrentUserId();
        User currentUser = managerService.userRepo.getUserById(userId);
        return currentUser.getAuthority().equals(AuthorityType.MANAGER);
    }
}
