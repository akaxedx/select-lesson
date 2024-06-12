package anda.selectlesson.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
public class MyLessonDTO implements Serializable {
    UsedTimeDTO usedTimeDTO;
    String lessonName;
}
