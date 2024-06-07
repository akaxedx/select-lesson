package anda.selectlesson.req.userReq;

import anda.selectlesson.model.base.BaseModel;
import anda.selectlesson.req.BaseReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class RegisterStudentReq extends BaseReq {
    // Student
    private Long teacherId;
    // Teacher
    private Integer studentNum;
    // Manager
}
