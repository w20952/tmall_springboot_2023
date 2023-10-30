package com.david.tmall_springboot_2023.service;

import com.david.tmall_springboot_2023.pojo.Product;
import com.david.tmall_springboot_2023.pojo.PropertyValue;
import com.david.tmall_springboot_2023.repository.PropertyValueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueService {

    @Autowired
    PropertyValueDAO propertyValueDAO;

    public List<PropertyValue> list(Product product){
        return propertyValueDAO.findAllByProduct(product);
    }
}
