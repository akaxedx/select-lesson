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
public class usedTimeDTO implements Serializable {
    Integer lessonTime;
    List<Integer> Monday;
    List<Integer> Tuesday;
    List<Integer> Wednesday;
    List<Integer> Thursday;
    List<Integer> Friday;
    List<Integer> Saturday;
    List<Integer> Sunday;
}
