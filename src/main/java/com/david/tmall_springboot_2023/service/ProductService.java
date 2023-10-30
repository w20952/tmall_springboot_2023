package com.david.tmall_springboot_2023.service;

import com.david.tmall_springboot_2023.pojo.Category;
import com.david.tmall_springboot_2023.pojo.Product;
import com.david.tmall_springboot_2023.repository.CategoryDAO;
import com.david.tmall_springboot_2023.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;

    public Product get(int id) {
        return productDAO.getOne(id);
    }

    public Page<Product> list(Pageable pageable, int cid) {
        Category category = categoryService.findOne(cid);
        return productDAO.findByCategory(pageable, category);
    }

    public void add(Product product) {
        productDAO.save(product);
    }

    public void delete(Product product) {
        productDAO.delete(product);
    }

    public void edit(Product product) {
        productDAO.save(product);
    }

    public void fill(Category category) {
        List<Product> products = productDAO.findByCategory(category);
        if (products != null) {
            productImageService.setFirstProductImage(products);
            category.setProducts(products);
        }
    }

    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    public void removeCategoryFromProducts(Category category) {
        List<Product> products = category.getProducts();
        if (products != null)
            for (Product product : products) {
                product.setCategory(null);
            }
    }

    public void removeCategoryFromProducts(List<Category> categories){
        for(Category category: categories){
            removeCategoryFromProducts(category);
        }
    }

}

















