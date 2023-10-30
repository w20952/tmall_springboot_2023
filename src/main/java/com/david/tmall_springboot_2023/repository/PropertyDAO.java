package com.david.tmall_springboot_2023.repository;

import com.david.tmall_springboot_2023.pojo.Category;
import com.david.tmall_springboot_2023.pojo.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PropertyDAO extends JpaRepository<Property, Integer>{

    Page<Property> findByCategory(Pageable pageable, Category category);
}
