package anda.selectlesson.req.studentReq;

import anda.selectlesson.req.BaseReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllTeachersReq extends BaseReq {
    private int pageNum;
}
