package com.ua.hotels.controllers;

import com.ua.hotels.models.Book;
import com.ua.hotels.models.Customer;
import com.ua.hotels.models.Hotel;
import com.ua.hotels.models.enums.Role;
import com.ua.hotels.service.CustomerService;
import com.ua.hotels.service.serv_impl.CustomerServiceImpl;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    public String index(Model model,
                        @AuthenticationPrincipal Customer user) {
        userRole(user, model);
        return "main";
    }

    static void userRole(Customer user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }
        model.addAttribute("admin_role", Role.ROLE_ADMIN);
        model.addAttribute("user_role", Role.ROLE_USER);
        model.addAttribute("hoteladmin_role", Role.ROLE_HOTELADMIN);

    }

    @PostMapping("/success")
    public String success(Model model,
                          @AuthenticationPrincipal Customer user) {
        userRole(user, model);
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/unsuccess")
    public String unsuccess(Model model) {
        model.addAttribute("error", "login.error");
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model, @AuthenticationPrincipal Customer user) {
        userRole(user, model);

        if (findActinveUserPage(model).equals("registration")) {
            return "login";
        }
        return findActinveUserPage(model);
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Customer user, Model model) {
        Customer customer = (Customer) customerService.loadUserById(user.getId());
        System.out.println(customer.getBooks());
        model.addAttribute("books", customer.getBooks());

        userRole(user, model);
        return "user";
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal Customer user, Model model) {
        Customer userById = (Customer) customerService.loadUserById(user.getId());
        model.addAttribute("user", userById);
        return "admin";

    }

    @GetMapping("/hoteladmin")
    public String hoteladmin(@AuthenticationPrincipal Customer user, Model model) {
        Customer userById = (Customer) customerService.loadUserById(user.getId());
        model.addAttribute("user", userById);
        model.addAttribute("hotels", userById.getHotels());
        System.out.println(userById.getHotels());
        return "hoteladmin";

    }


    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
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

    public String returnPath(Customer user) {
        Role role = user.getRole();
        if (role.equals(Role.ROLE_USER)) {
            return "redirect:/user";
        } else if (role.equals(Role.ROLE_ADMIN)) {
            return "redirect:/admin";
        } else {
            return "redirect:/hoteladmin";
        }
    }
}
