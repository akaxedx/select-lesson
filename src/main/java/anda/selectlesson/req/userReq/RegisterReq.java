package anda.selectlesson.req.userReq;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RegisterReq implements Serializable {
    private String username;
    private String password;
    private String confirmPassword;

}
