package com.david.tmall_springboot_2023.web;

import com.david.tmall_springboot_2023.pojo.*;
import com.david.tmall_springboot_2023.repository.ReviewDAO;
import com.david.tmall_springboot_2023.service.*;
import com.david.tmall_springboot_2023.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ForeRESTController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewDAO reviewDAO;
    @Autowired
    PropertyValueService propertyValueService;

    @GetMapping("forecart")
    public List<OrderItem> cart(HttpSession session){
        User user = (User)session.getAttribute("user");
        List<OrderItem> list = orderItemService.findByUserWithoutOrder(user);
        session.setAttribute("user", user);
        return list;
    }

    @GetMapping("foreBought")
    public List<Order> bought(HttpSession session){
        User user = userService.findOne(1);
        List<Order> orders = orderService.listByUser(user);
        orderItemService.fill(orders);

        return orders;
    }

    @PostMapping("register")
    public Result register(@RequestParam (value = "name")String name,
                           @RequestParam(value = "password")String password){
        User user= new User();
        user.setName(name);
        user.setPassword(password);

        userService.add(user);
        return  Result.success();
    }

    @GetMapping("forehome")
    public Object home(){
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.removeCategoryFromProducts(categories);
        return categories;
    }

    @GetMapping("foreproduct/{pid}")
    public Object product(@PathVariable(value = "pid")int pid){

        //获得产品。
        Product product = productService.get(pid);

        //设置产品的首个图片， 单个图片， 详情图片。
        productImageService.setFirstProductImage(product);
        productImageService.setDetailProductImages(product);
        productImageService.setSingleProductImages(product);

        List<PropertyValue> pvs = propertyValueService.list(product);

        //获得产品对应的详情图片。可以不通过此方法来获得产品的详情图片，通过在产品里面设置详情图片来获得详情图片。
        //即 productImageService.setDetailProductImages(product); 然后从前端通过 ${product.productDetailImages} 获取产品详情图片。
        List<ProductImage> images = productImageService.listDetailImages(product);

        List<Review> reviews = reviewDAO.findAllByProductOrderByIdDesc(product);
        int count = reviewDAO.countByProduct(product);
        product.setReviewCount(count);

        product.setSaleCount(0);

        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("reviews", reviews);
        map.put("count", count);
        map.put("pvs", pvs);
        map.put("images", images);

        return Result.success(map);
    }



















}
