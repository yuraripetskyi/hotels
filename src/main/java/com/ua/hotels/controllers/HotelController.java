package com.ua.hotels.controllers;

import com.ua.hotels.models.Hotel;
import com.ua.hotels.models.Room;
import com.ua.hotels.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class HotelController {


   @Autowired
   private HotelService hotelService;


    @PostMapping("/admin/create_hotel")
    public String create_hotel(Hotel hotel){

        hotelService.save(hotel);
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
