package com.example.demo.modules.entity;

import lombok.Data;

@Data
public class User {

    private String id;

    private String name;

    private String password;

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
