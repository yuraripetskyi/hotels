package com.ua.hotels.controllers;

import com.ua.hotels.dao.CustomerDAO;
import com.ua.hotels.models.Customer;
import com.ua.hotels.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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

@Controller
public class SearchController {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @GetMapping("/findUsers")
    public String findUsers(@RequestParam("user") String user , Model model){
        String[] param = user.split(" ");
        Customer customer = MainController.findActiveUser();
        model.addAttribute("user",customer);
        if (param.length == 1){
            String parametr = param[0];
            Set<Customer> byOneParam = findByOneParamByStream(parametr);
            model.addAttribute("users", byOneParam);
            return "admin" ;
        }
        else if (param.length == 2){
            String p1 = param[0];
            String p2 = param[1];
            Set<Customer> byTwoParam = findByTwoParamByStream(p1, p2);
            model.addAttribute("users", byTwoParam);
            return "admin";
        }else if(param.length >= 3){
            model.addAttribute("error","You entered something wrong");
            return "admin" ;
        }
        return "admin" ;
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

    public Set<Customer> findByOneParamByStream(String parametr){
        Set<Customer> byOneParam = customerDAO.findAll().stream().filter(customer -> customer.getName().equals(parametr) || customer.getSurname().equals(parametr)).collect(Collectors.toSet());
        Customer user = MainController.findActiveUser();
        int id = user.getId();
        Iterator<Customer> iter = byOneParam.iterator();
        while (iter.hasNext()){
            Customer next = iter.next();
            if(next.getId()== id){
                iter.remove();
            }
        }
        return byOneParam;
    }

    public Set<Customer> findByTwoParamByStream(String p1 , String p2){
        Set<Customer> byTwoParam = customerDAO.findAll().stream()
                .filter(customer -> (customer.getName().equals(p1) && customer.getSurname().equals(p2)) ||
                        (customer.getName().equals(p2) && customer.getSurname().equals(p1))).collect(Collectors.toSet());
        Customer user = MainController.findActiveUser();
        int id = user.getId();
        Iterator<Customer> iter = byTwoParam.iterator();
        while (iter.hasNext()){
            Customer next = iter.next();
            if(next.getId()== id){
                iter.remove();
            }
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

    @GetMapping("/block/{username}")
    public String blockUser(@PathVariable String username) {
        Customer activeUser = MainController.findActiveUser();
        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        user.setEnabled(false);
        customerDAO.save(user);
        return "redirect:/admin/"+activeUser.getUsername();
    }

    @GetMapping("/unblock/{username}")
    public String unblockUser(@PathVariable String username) {
        Customer user = (Customer) customerServiceImpl.loadUserByUsername(username);
        user.setEnabled(true);
        customerDAO.save(user);
        Customer activeUser = MainController.findActiveUser();
        return "redirect:/admin/"+activeUser.getUsername();
    }
}
