package com.ua.hotels.controllers;

import com.ua.hotels.models.Customer;
import com.ua.hotels.models.Hotel;
import com.ua.hotels.models.enums.Role;
import com.ua.hotels.service.CustomerService;
import com.ua.hotels.service.serv_impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@PropertySource("classpath:validation.properties")
public class MainController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @GetMapping("/")
    public String index(Model model) {
       return "main";
    }

    @PostMapping("/success")
    public String success(Model model) {
        return findActinveUserPage(model);
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @GetMapping("/unsuccess")
    public String unsuccess(Model model){
        model.addAttribute("error","login.error");
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (findActinveUserPage(model).equals("registration")) {
            return "login";
        }
        return findActinveUserPage(model);
    }

    @GetMapping("/user/{username}")
    public String user(@PathVariable String username, Model model) {
        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin/{username}")
    public String admin(@PathVariable String username, Model model) {
        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "admin";
    }

    @GetMapping("/hoteladmin/{username}")
    public String hoteladmin(@PathVariable String username, Model model) {
        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        List<Hotel> hotels = user.getHotels();
        model.addAttribute("hotels",hotels);
        model.addAttribute("user", user);
        return "hoteladmin";
    }


    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    public String findActinveUserPage(Model model) {
        String page = "registration";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            Object principal = auth.getPrincipal();
            if (principal.toString().contains("Username: admin")) {
                page = "admin_memory";
            } else {
                Customer principal_ = (Customer) principal;
                Customer user = (Customer) customerService.loadUserByUsername(principal_.getUsername());
                model.addAttribute("user", user);
                String path = returnPath(user);
                page = path;
            }
        }
        return page;
    }


    public static Customer findActiveUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)) {
            Customer user = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user;
        } else {
            return null;
        }
    }

    public String returnPath(Customer user)
    {
        Role role = user.getRole();
        if (role.equals(Role.ROLE_USER)) {
            return "redirect:/user/" + user.getUsername();
        } else if (role.equals(Role.ROLE_ADMIN)) {
            return "redirect:/admin/" + user.getUsername();
        } else {
            return "redirect:/hoteladmin/" + user.getUsername();
        }
    }
}
