package com.david.tmall_springboot_2023.service;

import com.david.tmall_springboot_2023.pojo.Order;
import com.david.tmall_springboot_2023.pojo.OrderItem;
import com.david.tmall_springboot_2023.pojo.User;
import com.david.tmall_springboot_2023.repository.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderItemService orderItemService;


    public Order get(int oid){
        return orderDAO.findOne(oid);
    }

    public Page<Order> list(int start, int size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = orderDAO.findAll(pageable);
        return page;
    }

    public void update(Order order){
        orderDAO.save(order);
    }

    public List<Order> listByUser(User user){
        return orderDAO.findAllByUser(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public float add(Order order, List<OrderItem> ois){
        float total = 0;
        orderDAO.save(order);

        for(OrderItem oi : ois){
            oi.setOrder(order);
            orderItemService.update(oi);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }



        return total;
    }
}
