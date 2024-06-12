package anda.selectlesson.model.dto;

import anda.selectlesson.model.po.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PageUserTeacherDTO implements Serializable {
    User user;
    String teacherIdentity;
}
