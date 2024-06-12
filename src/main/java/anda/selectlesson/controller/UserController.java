package anda.selectlesson.controller;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.model.dto.UserDTO;
import anda.selectlesson.model.po.Student;
import anda.selectlesson.model.po.User;
import anda.selectlesson.req.userReq.ConfirmReq;
import anda.selectlesson.req.userReq.LoginReq;
import anda.selectlesson.req.userReq.RegisterReq;
import anda.selectlesson.service.student.StudentService;
import anda.selectlesson.service.teacher.TeacherService;
import anda.selectlesson.service.user.UserService;
import anda.selectlesson.system.Response;
import anda.selectlesson.utils.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Tag(name = "用户请求")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Operation(summary = "注册请求")
    @PostMapping("/register")
    public Response<Void> register(@RequestBody RegisterReq req) {
        try {
            userService.registerUser(req);
        }catch (RuntimeException e) {
            return Response.error(e.getMessage());
        }
        return Response.ok();
    }

    @Operation(summary = "登录请求")
    @PostMapping("/login")
    public Response<UserDTO> login(@RequestBody LoginReq req) {
        try {
            UserDTO userDTO = userService.loginUser(req);
            return Response.ok(userDTO);
        }catch (RuntimeException e) {
            return Response.error(e.getMessage());
        }
    }
    @Operation(summary = "验证登录")
    @PostMapping("/confirm")
    public Response<Boolean> confirm(@RequestBody ConfirmReq req) throws IOException {
        Long currentUserId = JwtTokenUtils.getCurrentUserId();
        User user = userService.userRepo.getUserById(currentUserId);
        if (user == null) {
            return null;
        }
        if (Objects.equals(user.getAuthority(), AuthorityType.STUDENT)) {
            Student student = studentService.studentRepo.getByUserId(currentUserId);
            if (student == null) {
                return Response.ok(false);
            }
        }
        return Response.ok(user.getUsername());
    }
}
