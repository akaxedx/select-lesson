package anda.selectlesson.controller;

import anda.selectlesson.req.RegisterReq;
import anda.selectlesson.service.User.UserService;
import anda.selectlesson.system.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "用户请求")
public class UserController {
    @Autowired
    UserService userService;
    @Operation(summary = "注册请求")
    @PostMapping("/register")
    public Response<Void> register(@RequestBody RegisterReq req){
        try {
            userService.registerUser(req);
        }catch (RuntimeException e) {
            return Response.error(e.getMessage());
        }
        return Response.ok();
    }
}
