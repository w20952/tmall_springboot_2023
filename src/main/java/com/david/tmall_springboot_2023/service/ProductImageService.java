package com.david.tmall_springboot_2023.service;

import com.david.tmall_springboot_2023.pojo.Product;
import com.david.tmall_springboot_2023.pojo.ProductImage;
import com.david.tmall_springboot_2023.repository.ProductImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    public static final String type_single = "single";
    public static final String type_detail = "detail";

    @Autowired
    ProductImageDAO productImageDAO;

    public List<ProductImage> findAllByProduct(Product product){
        return productImageDAO.findAllByProduct(product);
    }

    public List<ProductImage> findAllByProductAndType(Product product, String type){
        return productImageDAO.findAllByProductAndType(product, type);
    }

    public void add(ProductImage productImage){
        productImageDAO.save(productImage);
    }

    public void delete(int id){
        productImageDAO.delete(id);
    }

    public void setFirstProductImage(Product product){
        List<ProductImage> singleImages = findAllByProductAndType(product, type_single);
        if(!singleImages.isEmpty())
            product.setFirstProductImage(singleImages.get(0));
        else
            product.setFirstProductImage(new ProductImage());
    }

    public void setFirstProductImage(List<Product> products){
        for(Product product : products){
            setFirstProductImage(product);
        }
    }

    public List<ProductImage> listDetailImages(Product product){
        return productImageDAO.findAllByProductAndType(product, type_detail);
    }

    public void setSingleProductImages(Product product){
        List<ProductImage> productImages = productImageDAO.findAllByProductAndType(product, type_single);
        product.setProductSingleImages(productImages);
    }

    public void setDetailProductImages(Product product){
        List<ProductImage> productImages = productImageDAO.findAllByProductAndType(product, type_detail);
        product.setProductDetailImages(productImages);
    }


































}
