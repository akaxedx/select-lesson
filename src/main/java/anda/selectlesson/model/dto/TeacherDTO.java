package anda.selectlesson.model.dto;

import anda.selectlesson.model.po.Lesson;
import anda.selectlesson.model.po.Teacher;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO implements Serializable {
    Teacher teacher;
    Boolean isChoose;
    String username;
}