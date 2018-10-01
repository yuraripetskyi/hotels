package com.ua.hotels.controllers;


import com.ua.hotels.models.Customer;
import com.ua.hotels.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/change/user/{id}")
    public String changeUser(@PathVariable int id,
                             Model model){
        Customer customer = (Customer) customerService.loadUserById(id);
        model.addAttribute("user", customer);
        return "changeCustomer";
    }
    @PostMapping("/change/customer/{id}")
    public String changeCustomer(@PathVariable int id,
                                 @RequestParam String username,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam int age,
                                 @RequestParam String city,
                                 @RequestParam String email){

        Customer customer = (Customer)customerService.loadUserById(id);
        if (username!=null){
            customer.setUsername(username);
        }
        if (name!=null){
            customer.setName(name);
        }
        if(surname!=null){
            customer.setSurname(surname);
        }

        if (email!=null){
            customer.setEmail(email);
        }
        customerService.save(customer);
        return "redirect:/";
    }
}
