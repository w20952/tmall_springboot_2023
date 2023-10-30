package com.david.tmall_springboot_2023.service;

import com.david.tmall_springboot_2023.pojo.Category;
import com.david.tmall_springboot_2023.pojo.Property;
import com.david.tmall_springboot_2023.repository.PropertyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    PropertyDAO propertyDAO;
    @Autowired
    CategoryService categoryService;

    public Page<Property> list(Pageable pageable, int cid){
        Category category = categoryService.findOne(cid);
        return propertyDAO.findByCategory(pageable, category);
    }

    public void add(Property property){
        propertyDAO.save(property);
    }

    public void delete(Property property){
        propertyDAO.delete(property);
    }

    public Property get(int id){
        return propertyDAO.findOne(id);
    }

    public void edit(Property property){
        propertyDAO.save(property);
    }
}
