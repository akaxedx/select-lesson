package anda.selectlesson.req.managerReq;

import anda.selectlesson.req.BaseReq;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditTeacherReq extends BaseReq {
    private Long userId;
    private String identity;
}
