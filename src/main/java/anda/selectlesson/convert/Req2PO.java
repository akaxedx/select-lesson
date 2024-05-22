package anda.selectlesson.convert;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.model.po.User;
import anda.selectlesson.req.RegisterReq;
import anda.selectlesson.system.UserContext;

public class Req2PO {
    public static User UserReq2PO(RegisterReq req) {
        User user = new User();
        user.setPassword(req.getPassword());
        user.setUsername(req.getUsername());
        user.setAuthority(AuthorityType.STUDENT);
        user.setCreateBy(req.getUsername());
        return user;
    }
}
