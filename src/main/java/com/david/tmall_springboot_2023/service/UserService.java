package com.david.tmall_springboot_2023.service;

import com.david.tmall_springboot_2023.pojo.User;
import com.david.tmall_springboot_2023.repository.UserDAO;
import com.david.tmall_springboot_2023.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = userDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public User findOne(int id){
        return userDAO.findOne(id);
    }

    public void add(User user){
        userDAO.save(user);
    }

    public User findByUsernameAndPassword(String username, String password){
        return userDAO.findByNameAndPassword(username, password);
    }
}
