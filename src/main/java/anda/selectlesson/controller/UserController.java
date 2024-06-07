package anda.selectlesson.controller;

import anda.selectlesson.model.dto.UserDTO;
import anda.selectlesson.req.userReq.LoginReq;
import anda.selectlesson.req.userReq.RegisterReq;
import anda.selectlesson.req.userReq.RegisterStudentReq;
import anda.selectlesson.service.user.UserService;
import anda.selectlesson.system.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "用户请求")
public class UserController {
    @Autowired
    UserService userService;
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
}
