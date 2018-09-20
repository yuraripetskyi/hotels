package com.ua.hotels.controllers;

import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class BookingController {

    @Autowired
    private RoomDAO roomDAO;

    @GetMapping("/main")
    private String MainPage(@RequestParam(required = false) String finder,
                            Model model) {
        List<Room> rooms = new ArrayList<>();
        List<Room> all = roomDAO.findAll();
        System.out.println(all);
        System.out.println("_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_");
        System.out.println(finder);
        System.out.println("_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_");

       if(finder != null){
           System.out.println(rooms);
           System.out.println(all);
           rooms.addAll(all.stream()
                   .filter(room -> room.getHotel().getCity().equals(finder)).collect(Collectors.toList()));
           System.out.println(rooms);
           rooms.addAll(all.stream()
                   .filter(room -> room.getHotel().getName().equals(finder)).collect(Collectors.toList()));
           System.out.println(rooms);

       }
        model.addAttribute("rooms", rooms);
        return "main";
    }
}
//    @PostMapping("/find/room")
//    public String findRoom(@RequestParam String finder,
//                           Model model){
//        List<Room> rooms = new ArrayList<>();
//        List<Room> all = roomDAO.findAll();
//        rooms.addAll(all.stream()
//                .filter(room -> room.getHotel().getCity().equals(finder)).collect(Collectors.toList()));
//        rooms.addAll(all.stream()
//                        .filter(room -> room.getHotel().getName().equals(finder)).collect(Collectors.toList()));
//
//                model.addAttribute("rooms", rooms);
//        return "main";
//    }
//}
