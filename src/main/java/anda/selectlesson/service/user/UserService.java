package anda.selectlesson.service.user;

import anda.selectlesson.convert.Req2PO;
import anda.selectlesson.model.dto.UserDTO;
import anda.selectlesson.model.po.Student;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.UserRepo;
import anda.selectlesson.req.LoginReq;
import anda.selectlesson.req.RegisterReq;
import anda.selectlesson.req.RegisterStudentReq;
import anda.selectlesson.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserService {
    @Autowired
    UserRepo userRepo;
    public void registerUser(RegisterReq req) {
        confirmRegisterReq(req);
        User user = Req2PO.UserReq2PO(req);
        userRepo.save(user);
    }

    public UserDTO loginUser(LoginReq req) {
        confirmLoginReq(req);
        User user= userRepo.getUserByUsernameAndPasswordAndAuthority(req.getUsername(),
                req.getPassword(),
                req.getAuthority());
        if (null == user) {
            throw new RuntimeException("没有该用户，请去注册");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setAuthority(user.getAuthority());
        userDTO.setUsername(user.getUsername());
        userDTO.setToken(JwtTokenUtils.getToken(user.getId().toString(), user.getPassword()));
        return userDTO;
    }

    public Long registerStudent(RegisterStudentReq req) throws IOException {
//        Student student = new Student();
//        student.setUserId(JwtTokenUtils.getCurrentUser().getId());
        return 1L;
    }

    private void confirmRegisterReq(RegisterReq req) {
        if (null == req.getUsername() || null == req.getPassword() || null == req.getConfirmPassword()) {
            throw new RuntimeException("请求不能为null");
        } else if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("两次密码不相等");
        }
        User user = userRepo.getUserByUsername(req.getUsername());
        if (user != null) {
            throw new RuntimeException("该用户名已被注册");
        }
    }

    private void confirmLoginReq(LoginReq req) {
        if (null == req.getUsername() || null == req.getPassword() || null == req.getAuthority()) {
            throw new RuntimeException("请求不能为null");
        }
    }
}
