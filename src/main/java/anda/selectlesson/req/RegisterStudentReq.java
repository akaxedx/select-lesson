package anda.selectlesson.req;

import anda.selectlesson.model.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class RegisterStudentReq extends BaseReq {
    // Student
    private Long teacherId;
    // Teacher
    private Integer studentNun;
    // Manager
}
