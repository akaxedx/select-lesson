package anda.selectlesson.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginReq implements Serializable {
    private String username;
    private String password;
    private String authority;
}
