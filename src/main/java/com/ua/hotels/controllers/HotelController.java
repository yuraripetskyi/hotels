package com.ua.hotels.controllers;

import com.ua.hotels.dao.HotelDAO;
import com.ua.hotels.models.Customer;
import com.ua.hotels.models.Hotel;
import com.ua.hotels.service.CustomerService;
import com.ua.hotels.service.CustomerServiceImpl;
import com.ua.hotels.utils.CustomerEditor;
import com.ua.hotels.utils.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HotelController {

    @Autowired
    private Environment environment;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private CustomerEditor customerEditor;

    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private HotelDAO hotelDAO;

    @GetMapping("/create/hotel")
    public String createHotel(){
        return "createHotel";
    }

    @PostMapping("/save/hotel")
    public String saveHotel(Hotel hotel){
        Customer user = MainController.findActiveUser();
        hotel.setCustomer(user);
        hotelDAO.save(hotel);
        return "redirect:/user/" + user.getUsername();
    }
}
