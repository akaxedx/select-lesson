package anda.selectlesson.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class StudentIdsDTO {
    private Long studentId;
    private Boolean exit;
}
