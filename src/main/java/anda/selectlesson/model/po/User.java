package anda.selectlesson.model.po;

import anda.selectlesson.model.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user")
public class User extends BaseModel{
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "authority")
    private String authority;
    @Column(name = "badNum")
    private Integer badNum;
}
