package com.david.tmall_springboot_2023.repository;

import com.david.tmall_springboot_2023.pojo.Order;
import com.david.tmall_springboot_2023.pojo.OrderItem;
import com.david.tmall_springboot_2023.pojo.Product;
import com.david.tmall_springboot_2023.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer>{

    List<OrderItem> findAllByOrder(Order order);

    List<OrderItem> findAllByUser(User user);

    List<OrderItem> findAllByProduct(Product product);
}
