package com.david.tmall_springboot_2023.repository;

import com.david.tmall_springboot_2023.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer>{
}
