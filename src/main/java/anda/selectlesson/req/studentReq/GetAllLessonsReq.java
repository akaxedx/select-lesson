package anda.selectlesson.req.studentReq;

import anda.selectlesson.req.BaseReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllLessonsReq extends BaseReq {
    private String lessonName;
    private int pageNum;
}
