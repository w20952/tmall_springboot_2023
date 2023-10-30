package com.david.tmall_springboot_2023.repository;

import com.david.tmall_springboot_2023.pojo.Category;
import com.david.tmall_springboot_2023.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {

    Page<Product> findByCategory(Pageable pageable, Category category);

    List<Product> findByCategory(Category category);


}
