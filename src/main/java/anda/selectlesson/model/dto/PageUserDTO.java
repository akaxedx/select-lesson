package anda.selectlesson.model.dto;

import anda.selectlesson.model.po.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PageUserDTO implements Serializable {
    List<PageUserTeacherDTO> users;
    int pageTotal;
}
