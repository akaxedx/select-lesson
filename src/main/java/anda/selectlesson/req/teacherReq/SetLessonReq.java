package anda.selectlesson.req.teacherReq;

import anda.selectlesson.req.BaseReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetLessonReq extends BaseReq {
    private Integer time;
    private String lessonName;
}
