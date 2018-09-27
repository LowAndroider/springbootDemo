package com.example.demo.modules.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(generator = "user_uuid")
    @GenericGenerator(strategy = "uuid.hex",name="user_uuid")
    @Getter
    @Setter
    private String id;

    @Column
    @Getter
    @Setter
    private String name;

    @Column
    @Getter
    @Setter
    private String password;

    @Column
    @Getter
    @Setter
    private String permissions;
}
