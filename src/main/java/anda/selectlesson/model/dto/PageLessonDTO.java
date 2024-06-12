package anda.selectlesson.model.dto;

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
public class PageLessonDTO implements Serializable {
    List<LessonDTO> lessonDTOS;
    int pageTotal;
}
