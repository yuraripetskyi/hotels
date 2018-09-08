package com.ua.hotels.controllers;

import com.ua.hotels.dao.HotelDAO;
import com.ua.hotels.dao.PhoneDAO;
import com.ua.hotels.models.Customer;
import com.ua.hotels.models.Hotel;
import com.ua.hotels.models.Phone;
import com.ua.hotels.service.CustomerService;
import com.ua.hotels.service.CustomerServiceImpl;
import com.ua.hotels.utils.CustomerEditor;
import com.ua.hotels.utils.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
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

    @Autowired
    private PhoneDAO phoneDAO;

    @GetMapping("/create/hotel")
    public String createHotel() {
        return "createHotel";
    }

    @PostMapping("/save/hotel")
    public String saveHotel(Hotel hotel, @RequestParam(value = "phones") String[] phones) {
        Customer user = MainController.findActiveUser();
        hotel.setCustomer(user);
        hotelDAO.save(hotel);
        for (String phone : phones) {
            Phone phonec = new Phone(phone);
            phonec.setHotel(hotel);
            phoneDAO.save(phonec);
        }
        return "redirect:/hoteladmin/" + user.getUsername();
    }

    @GetMapping("/hotel/{id}")
    public String hotel(@PathVariable String id, Model model) {
        Hotel hotel = hotelDAO.findById(Integer.parseInt(id)).get();
        model.addAttribute("hotel", hotel);
        return "hotel";
    }

    @DeleteMapping("/delete/hotel/{id}")
    public String deleteHotel(@PathVariable String id) {
        hotelDAO.delete(hotelDAO.findById(Integer.parseInt(id)).get());
        Customer user = MainController.findActiveUser();
        return "redirect:/hoteladmin/"+user.getUsername();
    }

    @PostMapping("/upload/photos/hotel/{id}")
    public String uploadPhotos(@PathVariable int id,
                               @RequestParam(value = "images") File[] images,
                               Model model){
        Hotel hotel = hotelDAO.findById(id).get();
        model.addAttribute("hotel", hotel);






        return "hotel";
    }

}


