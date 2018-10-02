package com.ua.hotels.controllers;


import com.ua.hotels.dao.CustomerDAO;
import com.ua.hotels.models.Customer;
import com.ua.hotels.models.enums.Role;
import com.ua.hotels.service.serv_impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @GetMapping("/block/{username}")
    public String blockUser(@PathVariable String username) {
        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        user.setEnabled(false);
        customerDAO.save(user);
        return "redirect:/";
    }

//    @GetMapping("/admin_memory")
//    public String adminMemoryPage(){
//
//
//        return "admin_memory";
//    }



    @GetMapping("/unblock/{username}")
    public String unblockUser(@PathVariable String username) {
        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        user.setEnabled(true);
        customerDAO.save(user);
        return "redirect:/";
    }

    @GetMapping("/makeHotelAdmin/{username}")
    public String makeHotelAdmin(@PathVariable String username){

        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        user.setRole(Role.ROLE_HOTELADMIN);
        customerDAO.save(user);
        return "redirect:/";
    }

    @GetMapping("/makeAdmin/{username}")
    public String makeAdmin(@PathVariable String username){

        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        user.setRole(Role.ROLE_ADMIN);
        customerDAO.save(user);
        return "redirect:/";
    }

    @GetMapping("/makeUser/{username}")
    public String makeUser(@PathVariable String username){

        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        user.setRole(Role.ROLE_USER);
        customerDAO.save(user);
        return "redirect:/";
    }

}
