package com.ua.hotels.controllers;


import com.ua.hotels.models.Customer;
import com.ua.hotels.models.enums.Role;
import com.ua.hotels.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/change/user")
    public String changeUser(
                             Model model, @AuthenticationPrincipal Customer customer){
        model.addAttribute("user", customer);
        return "changeCustomer";
    }
    @PostMapping("/change/customer")
    public String changeCustomer(
                                 @RequestParam String username,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam String email,@AuthenticationPrincipal Customer customer){

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
        if(customer.getRole().equals(Role.ROLE_ADMIN)){
            return "redirect/:admin";
        }
        if (customer.getRole().equals(Role.ROLE_HOTELADMIN)){
            return "redirect:/hoteladmin";
        }
        return "redirect:/user";
    }
}
