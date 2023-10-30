package com.david.tmall_springboot_2023.repository;

import com.david.tmall_springboot_2023.pojo.Product;
import com.david.tmall_springboot_2023.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDAO extends JpaRepository<Review, Integer>{
    List<Review> findAllByProductOrderByIdDesc(Product product);
    int countByProduct(Product product);
}
