package com.ua.hotels.controllers;


import com.ua.hotels.dao.CustomerDAO;
import com.ua.hotels.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class ValidationController {

    @Autowired
    CustomerDAO customerDAO;

    public static boolean usernamevalidation(CustomerDAO customerDAO, String name) {
        List<Customer> customers = customerDAO.findAll();
        Stream<Customer> stream = customers.stream();
        if (stream.filter(customer -> customer.getUsername().equals(name)).findAny().isPresent()) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean emailvalidation(CustomerDAO customerDAO, String name) {

        List<Customer> customers = customerDAO.findAll();
        Stream<Customer> stream = customers.stream();
        if (stream.filter(customer -> customer.getEmail().equals(name)).findAny().isPresent()) {
            return true;
        } else {
            return false;
        }


    }

}





