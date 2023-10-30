package com.david.tmall_springboot_2023.service;

import com.david.tmall_springboot_2023.pojo.Order;
import com.david.tmall_springboot_2023.pojo.OrderItem;
import com.david.tmall_springboot_2023.pojo.Product;
import com.david.tmall_springboot_2023.pojo.User;
import com.david.tmall_springboot_2023.repository.OrderItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    OrderItemDAO orderItemDAO;
    @Autowired
    ProductImageService productImageService;

    public List<OrderItem> findByOrder(Order order){
        return orderItemDAO.findAllByOrder(order);
    }

    public List<OrderItem> listByProduct(Product product){
        return orderItemDAO.findAllByProduct(product);
    }

    public List<OrderItem> findByUser(User user){
        List<OrderItem> ois =  orderItemDAO.findAllByUser(user);

        for(OrderItem oi : ois){
            Product product = oi.getProduct();
            productImageService.setFirstProductImage(product);
        }
        return ois;
    }

    public List<OrderItem> findByUserWithoutOrder(User user){
        List<OrderItem> ois = orderItemDAO.findAllByUser(user);

        List<OrderItem> returnOis = new ArrayList<>();

        for(OrderItem oi : ois){
            if (oi.getOrder() == null)
                returnOis.add(oi);
        }

        for(OrderItem oi : returnOis){
            Product product = oi.getProduct();
            productImageService.setFirstProductImage(product);
        }

        return returnOis;
    }

    public OrderItem findByOrderItem(int id){
         OrderItem oi = orderItemDAO.findOne(id);
         Product product = oi.getProduct();
         productImageService.setFirstProductImage(product);

         return oi;
    }

    public void update(OrderItem oi){
        orderItemDAO.save(oi);
    }

    public void add(OrderItem orderItem){
        orderItemDAO.save(orderItem);
    }


    public void fill(Order order){
        List<OrderItem> orderItems = findByOrder(order);
        float total = 0;
        int totalNumber = 0;

        for(OrderItem oi : orderItems){
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
            productImageService.setFirstProductImage(oi.getProduct());
        }

        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(orderItems);
    }

    public void fill(List<Order> orders){
        for(Order order : orders){
            fill(order);
        }
    }

    public void removeOrderFromOrderItem(Order order){
        List<OrderItem> orderItems = order.getOrderItems();
        for(OrderItem orderItem : orderItems){
            orderItem.setOrder(null);
        }
    }

    public void removeOrderFromOrderItem(List<Order> orders){
        for(Order order : orders){
            removeOrderFromOrderItem(order);
        }
    }


}




















