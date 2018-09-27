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
    @Column(length = 32)
    @GeneratedValue(generator = "user_uuid")
    @GenericGenerator(strategy = "uuid.hex",name="user_uuid")
    @Getter
    @Setter
    private String id;

    @Column(length = 40,unique = true)
    @Getter
    @Setter
    private String name;

    @Column(length = 32)
    @Getter
    @Setter
    private String password;

    @Column(length = 200)
    @Getter
    @Setter
    private String permissions;

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof User) {
            if(!((User) obj).id.equals(this.id))
                return false;

            return ((User) obj).name.equals(this.name);

        } else {
            return false;
        }

    }
}
