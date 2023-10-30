package com.david.tmall_springboot_2023.repository;

import com.david.tmall_springboot_2023.pojo.Product;
import com.david.tmall_springboot_2023.pojo.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageDAO extends JpaRepository<ProductImage, Integer>{

    List<ProductImage> findAllByProduct(Product product);

    List<ProductImage> findAllByProductAndType(Product product, String type);
}
