package com.david.tmall_springboot_2023.service;

import com.david.tmall_springboot_2023.pojo.Product;
import com.david.tmall_springboot_2023.pojo.Review;
import com.david.tmall_springboot_2023.repository.ReviewDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewDAO reviewDAO;

    List<Review> findAllByProduct(Product product){
        return reviewDAO.findAllByProductOrderByIdDesc(product);
    }

    int countByProduct(Product product){
        return reviewDAO.countByProduct(product);
    }
}
