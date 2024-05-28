package anda.selectlesson.model.base;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    public BaseModel() {
        createAt = LocalDateTime.now();
        updateAt = createAt;
    }
}
