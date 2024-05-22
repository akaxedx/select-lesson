package anda.selectlesson.model.po;

import anda.selectlesson.model.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "student")
public class Student extends BaseModel {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "class_id")
    private Long classId;
    @Column(name = "lesson_ids", columnDefinition = "JSON")
    private String lessonIds;
    @Column(name = "class_teacher_id")
    private Long classTeacherId;
}
