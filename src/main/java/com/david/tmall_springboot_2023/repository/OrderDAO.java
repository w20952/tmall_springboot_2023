package com.david.tmall_springboot_2023.repository;

import com.david.tmall_springboot_2023.pojo.Order;
import com.david.tmall_springboot_2023.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Integer> {

    public List<Order> findAllByUser(User user);
}
