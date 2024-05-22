package anda.selectlesson.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseReq implements Serializable {
    String token;
}
