package com.david.tmall_springboot_2023.repository;

import com.david.tmall_springboot_2023.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {

    User findByNameAndPassword(String name, String password);
}
