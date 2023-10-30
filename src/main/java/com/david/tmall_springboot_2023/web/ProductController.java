package com.david.tmall_springboot_2023.web;

import com.david.tmall_springboot_2023.pojo.Product;
import com.david.tmall_springboot_2023.service.ProductImageService;
import com.david.tmall_springboot_2023.service.ProductService;
import com.david.tmall_springboot_2023.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;

    @GetMapping("products/{cid}")
    public Page4Navigator<Product> listProducts(@RequestParam(value = "start", defaultValue = "0") int start,
                                                @RequestParam(value = "size", defaultValue = "5") int size,
                                                @PathVariable(value = "cid") int cid){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start/5, size, sort);
        Page<Product> page =  productService.list(pageable,cid);
        productImageService.setFirstProductImage(page.getContent());
        return new Page4Navigator<>(page, 5);
    }
}
