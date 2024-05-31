package anda.selectlesson.model.po;

import anda.selectlesson.model.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "lesson")
public class Lesson extends BaseModel {
    @Column(name = "max_num")
    private Integer maxNum;
    @Column(name = "current_num")
    private Integer currentNum;
    @Column(name = "student_ids", columnDefinition = "JSON")
    private String studentIds;
    @Column(name = "teacher_id")
    private Long teacherId;
    @Column(name = "lesson_name")
    private String lessonName;
    @Column(name = "time")
    private Integer time;
}
