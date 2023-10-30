package com.david.tmall_springboot_2023.web;

import com.david.tmall_springboot_2023.pojo.Category;
import com.david.tmall_springboot_2023.repository.CategoryDAO;
import com.david.tmall_springboot_2023.service.CategoryService;
import com.david.tmall_springboot_2023.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("categories")
    /*
    @start: 从第几个元素开始。
    @size: 每页里面的元素个数。
    navigatePages: 如果分页页码为【1 2 3 4 5】此时为 5。如果分页页码为【3 4 5】此时为3。

     */
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start,
                               @RequestParam(value = "size", defaultValue = "5") int size) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");

        Pageable pageable = new PageRequest(start/5, size, sort);
        Page<Category> page = categoryService.list(pageable);
        return new Page4Navigator<Category>(page, 5);

    }

    /*
    为了查看 Page 序列化的属性时做的实验。
     */
//    public Page<Category> list(@RequestParam(value = "start", defaultValue = "0")int start,
//                               @RequestParam(value = "size", defaultValue = "5")int size){
//        Sort sort = new Sort(Sort.Direction.ASC, "id");
//
//        Pageable pageable = new PageRequest(start/5, size, sort);
//        Page<Category> page = categoryService.list(pageable);
//
//        return page;
//    }

}
