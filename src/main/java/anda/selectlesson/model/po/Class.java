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
@Table(name = "class")
public class Class extends BaseModel {
    @Column(name = "class_teacher_id")
    private Long classTeacherId;
    @Column(name = "student_ids")
    private String studentIds;
}
