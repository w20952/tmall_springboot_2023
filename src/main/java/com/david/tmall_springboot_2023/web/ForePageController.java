package com.david.tmall_springboot_2023.web;

import com.david.tmall_springboot_2023.pojo.*;
import com.david.tmall_springboot_2023.service.OrderItemService;
import com.david.tmall_springboot_2023.service.OrderService;
import com.david.tmall_springboot_2023.service.ProductService;
import com.david.tmall_springboot_2023.service.UserService;
import com.david.tmall_springboot_2023.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ForePageController {

    @Autowired
    ForeRESTController foreRESTController;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @GetMapping("home")
    public String home(Model model) {
        List<Category> categories = (List<Category>) foreRESTController.home();
        model.addAttribute("cs", categories);
        return "fore/home";
    }

    @GetMapping("cart")
    public String cart(HttpSession session, Model model) {
        List<OrderItem> list = foreRESTController.cart(session);
        model.addAttribute("list", list);
        return "fore/cart";
    }

    @GetMapping("foreRegister")
    public String register() {
        return "fore/register";
    }

    @GetMapping("foreCheckLogin")
    @ResponseBody
    public Result checkLogin(HttpSession session) {
        if (session.getAttribute("user") == null)
            return Result.fail("no user");
        return Result.success();
    }


    @GetMapping("foreBuyOne")
    @ResponseBody
    public int buy(@RequestParam(value = "pid") int pid, @RequestParam(value = "num") int num, HttpSession session) {
        Product product = productService.get(pid);

        User user = (User)session.getAttribute("user");

        OrderItem oi = new OrderItem();
        oi.setUser(user);
        oi.setProduct(product);
        oi.setNumber(num);

        orderItemService.add(oi);
        System.out.println("oiid : " + oi.getId());

        return oi.getId();
    }

    @GetMapping("foreAddCart")
    @ResponseBody
    public Object buyAndAddCart(int pid, int num, HttpSession session) {

        Product product = productService.get(pid);
        int oiid = 0;

        User user = (User)session.getAttribute("user");

        boolean found = false;

        List<OrderItem> ois = orderItemService.findByUser(user);
        for (OrderItem oi : ois) {
            if (oi.getOrder() == null && oi.getProduct().getId() == product.getId()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }

         if (!found){
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setNumber(num);
            oi.setProduct(product);
            orderItemService.add(oi);
            oiid = oi.getId();
         }

        return oiid;
    }


    @GetMapping("buy")
    public String buy(@RequestParam(value = "oiid", required = false) List<Integer> oiidList, Model model, HttpSession session) {
        List<OrderItem> ois = new ArrayList<>();
        for (int oiid : oiidList) {
            OrderItem orderItem = orderItemService.findByOrderItem(oiid);
            ois.add(orderItem);
        }

        session.setAttribute("ois", ois);
        return "fore/buy";
    }

    //通过 "buyPage.html" 里面的 ajax 提交到 "ForePageController.java" 中的 "orders_ajax"。
    @PostMapping("orders_ajax")
    @ResponseBody
    public Object createOrderAjax(HttpSession session, @RequestBody Order order) {
        User user = (User) session.getAttribute("user");
//        if (user == null){
//            return "redirect:login";
//        }
        System.out.println("  ****************************  ");
        System.out.println("order : " + order);
        System.out.println("order Address : " + order.getAddress());
        System.out.println("order post : " + order.getPost());

        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");

        System.out.println(ois);
        for (OrderItem oi : ois) {
            System.out.println(oi.getId());
        }

        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderService.waitPay);

        float total = orderService.add(order, ois);

        Map<String, Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);

        return Result.success(map);
    }

    @GetMapping("alipay")
    public String alipay() {
        return "fore/alipay";
    }

    @GetMapping("foreproduct")
    public String product(@RequestParam(value = "pid") int pid, Model model) {

        Result result = (Result) foreRESTController.product(pid);
        Map<String, Object> map = (Map) result.getData();

        model.addAttribute("product", map.get("product"));
        model.addAttribute("reviews", map.get("review"));
        model.addAttribute("count", map.get("count"));
        model.addAttribute("pvs", map.get("pvs"));
        model.addAttribute("images", map.get("images"));

        return "fore/product";
    }

    @GetMapping("foreBoughtPage")
    public String bought(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        user = userService.findOne(1);
        if (user != null) {
            List<Order> orders = foreRESTController.bought(session);
            model.addAttribute("orders", orders);
            return "fore/bought";
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("login")
    public String login() {
        return "fore/login";
    }

    @PostMapping("foreLogin")
    public String login(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password,
                        HttpSession session) {
        User user = userService.findByUsernameAndPassword(name, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:home";
        }

        return "redirect:login";
    }


}























