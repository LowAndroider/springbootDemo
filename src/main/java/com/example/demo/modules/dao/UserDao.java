package com.example.demo.modules.dao;

import com.example.demo.modules.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name = "t_user")
public interface UserDao extends JpaRepository<User, String> {

    /**
     * @param id
     * @return
     */
    User findUserById(String id);

    User findUserByName(String name);

    User save(User user);
}
