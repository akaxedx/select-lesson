package anda.selectlesson.req.teacherReq;

import anda.selectlesson.req.BaseReq;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SetLessonReq extends BaseReq {
    List<Integer> Monday;
    List<Integer> Tuesday;
    List<Integer> Wednesday;
    List<Integer> Thursday;
    List<Integer> Friday;
    List<Integer> Saturday;
    List<Integer> Sunday;
    Date startDate;
    Date endDate;
    String lessonName;
    Long roomId;
}
