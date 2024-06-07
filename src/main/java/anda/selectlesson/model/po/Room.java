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
@Table(name = "room")
public class Room extends BaseModel {
    @Column(name = "room_id")
    private String roomId;
    @Column(name = "floor")
    private Integer floor;
    @Column(name = "building")
    private String building;
    @Column(name = "num")
    private Integer num;
    @Column(name = "used")
    private Boolean used;
}
