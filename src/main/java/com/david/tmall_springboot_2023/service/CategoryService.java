package com.david.tmall_springboot_2023.service;

import com.david.tmall_springboot_2023.pojo.Category;
import com.david.tmall_springboot_2023.repository.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryDAO categoryDAO;

//    public List<Category> list(){
//        return categoryDAO.findAll();
//    }

    public Page<Category> list(Pageable pageable){
        return categoryDAO.findAll(pageable);
    }

    public void add(Category category){
        categoryDAO.save(category);
    }

    public Category findOne(int cid){
        return categoryDAO.findOne(cid);
    }

    public void deleteOne(int cid){
        categoryDAO.delete(cid);
    }

    public void update(Category category){
        categoryDAO.save(category);
    }

    public List<Category> list(){
        return categoryDAO.findAll();
    }

}
