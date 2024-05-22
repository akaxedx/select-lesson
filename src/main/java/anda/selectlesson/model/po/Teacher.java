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
@Table(name = "teacher")
public class Teacher extends BaseModel {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "class_id")
    private Long classId;
    @Column(name = "lesson_ids", columnDefinition = "JSON")
    private String lessonIds;
    @Column(name = "identity")
    private String identity;
}
