package com.ua.hotels.controllers;

import com.ua.hotels.dao.CustomerDAO;
import com.ua.hotels.models.Customer;
import com.ua.hotels.models.enums.Role;
import com.ua.hotels.service.serv_impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ua.hotels.controllers.MainController.userRole;

@Controller
public class SearchController {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @GetMapping("/findUsers")
    public String findUsers(@RequestParam("user") String user, Model model,@AuthenticationPrincipal Customer user1) {

        model.addAttribute("admin_role", Role.ROLE_ADMIN);
        model.addAttribute("user_role", Role.ROLE_USER);
        model.addAttribute("hoteladmin_role", Role.ROLE_HOTELADMIN);

        String[] param = user.split(" ");
        String path = "admin";
        if(!isMemoryAdmin()){

            model.addAttribute("user",user1);
        }else{
            path = "admin_memory";
        }
        if (param.length == 1) {
            String parametr = param[0];
            Set<Customer> byOneParam = findByOneParamByStream(parametr);
            System.out.println(byOneParam);
            model.addAttribute("users", byOneParam);
            return path;
        } else if (param.length == 2) {
            String p1 = param[0];
            String p2 = param[1];
            Set<Customer> byTwoParam = findByTwoParamByStream(p1, p2);
            System.out.println(byTwoParam);
            model.addAttribute("users", byTwoParam);
            return path;
        } else if (param.length >= 3) {
            model.addAttribute("error", "You entered something wrong");
            return path;
        }
        return path;
    }



//    public Set<Customer> findByOneParam(String parametr){
//        List<Customer> allByName = customerDAO.findAllByName(parametr);
//        List<Customer> allBySurname = customerDAO.findAllBySurname(parametr);
//        allByName.addAll(allBySurname);
//        Set<Customer> allByName1 = new HashSet<>(allByName);
//        Customer user = MainController.findActiveUser();
//        int id = user.getId();
//        Iterator<Customer> iter = allByName1.iterator();
//        while (iter.hasNext()){
//            Customer next = iter.next();
//            if(next.getId()== id){
//                iter.remove();
//            }
//        }
//        return allByName1;
//    }

    public Set<Customer> findByOneParamByStream(String parametr) {
        Set<Customer> byOneParam = customerDAO.findAll().stream().filter(customer -> customer.getName().equals(parametr) || customer.getSurname().equals(parametr)).collect(Collectors.toSet());
        if(!isMemoryAdmin()){
            Customer user = MainController.findActiveUser();
            int id = user.getId();
            Iterator<Customer> iter = byOneParam.iterator();
            System.out.println();
            while (iter.hasNext()) {
                Customer next = iter.next();
                if (next.getId() == id) {
                    iter.remove();
                }
            }
            return byOneParam;
        }
        return byOneParam;
    }

    public Set<Customer> findByTwoParamByStream(String p1, String p2) {
        Set<Customer> byTwoParam = customerDAO.findAll().stream()
                .filter(customer -> (customer.getName().equals(p1) && customer.getSurname().equals(p2)) ||
                        (customer.getName().equals(p2) && customer.getSurname().equals(p1))).collect(Collectors.toSet());
        if(!isMemoryAdmin()) {
            Customer user = MainController.findActiveUser();
            int id = user.getId();
            System.out.println();
            Iterator<Customer> iter = byTwoParam.iterator();
            while (iter.hasNext()) {
                Customer next = iter.next();
                if (next.getId() == id) {
                    iter.remove();
                }
            }
            return byTwoParam;
        }
        return byTwoParam;
    }

//    public Set<Customer> findByTwoParam(String p1 , String p2){
//        List<Customer> list1 = customerDAO.findAllByNameAndSurname(p1, p2);
//        List<Customer> list2 = customerDAO.findAllByNameAndSurname(p2, p1);
//        list1.addAll(list2);
//        Set<Customer> list = new HashSet<>(list1);
//        Customer user = MainController.findActiveUser();
//        int id = user.getId();
//        Iterator<Customer> iter = list.iterator();
//        while (iter.hasNext()){
//            Customer next = iter.next();
//            if(next.getId()== id){
//                iter.remove();
//            }
//        }
//        return list;
//    }

    @GetMapping("/guest/{username}")
    public String guestPage(@PathVariable String username, Model model) {
        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "guest";
    }


    public boolean isMemoryAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            Object principal = auth.getPrincipal();
            if (principal.toString().contains("Username: admin")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
