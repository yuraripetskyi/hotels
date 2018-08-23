package com.ua.hotels.controllers;

import com.ua.hotels.models.*;
import com.ua.hotels.service.AdressService;
import com.ua.hotels.service.ContactService;
import com.ua.hotels.service.CustomerService;
import com.ua.hotels.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class HotelController {


   @Autowired
   private HotelService hotelService;
   @Autowired
   private CustomerService customerService;

//   @Autowired
//   private AdressService adressService;
//
//   @Autowired
//   private ContactService contactService;

    @PostMapping("/admin/create_hotel")
    public String create_hotel(@RequestParam String name ,
                               @RequestParam String city,
                               @RequestParam String house,
                               @RequestParam String street,
                               @RequestParam byte stars,
                               @RequestParam String email,
                               @RequestParam String phone,
                               @RequestParam String description,
                               Model model){
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken) ) {
            Customer user = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);

/*

dorobyty!!!
 */

            System.out.println("1");
            Hotel hotel = new Hotel(name, city,street, house, email, phone, stars, description);
            hotel.setAdmin(user);

            System.out.println(hotel);

            hotelService.save(hotel);
        }
        return "admin";
    }


    @PostMapping("/admin/add-room-to-hotel")
    public String add_room(@RequestParam String hotel_name, Room room){
        List<Hotel> hotels = hotelService.findAll();
        Stream<Hotel> stream = hotels.stream();
        Hotel thishotel =  stream.filter(hotel -> hotel.getName().equals(hotel_name)).findAny().get();
        List<Room> rooms = thishotel.getRooms();
        rooms.add(room);
        hotelService.save(thishotel);

        return "admin";
    }
}
