package anda.selectlesson.service.User;

import anda.selectlesson.convert.Req2PO;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.UserRepo;
import anda.selectlesson.req.RegisterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    UserRepo userRepo;
    public void registerUser(RegisterReq req) {
        confirmRegisterReq(req);
        User user = Req2PO.UserReq2PO(req);
        userRepo.save(user);
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
}
