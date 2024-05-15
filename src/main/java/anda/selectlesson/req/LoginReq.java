package anda.selectlesson.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq extends BaseReq{
    private String username;
    private String password;
    private String authority;
}
