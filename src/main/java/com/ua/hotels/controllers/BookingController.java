package com.ua.hotels.controllers;

import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class BookingController {

    @Autowired
    private RoomDAO roomDAO;

    @GetMapping("/main")
    private String MainPage(@RequestParam(required = false, defaultValue = "") String finder,
                            @RequestParam(required = false,defaultValue = "") Integer countOfGuests,
                            @RequestParam(required = false, defaultValue = "") String filter,
                            Model model) {
        List<Room> rooms = new ArrayList<>();

        //Пошук румів по місту чи назві готелю і кількості кімнат відразу, я подумав що краще зробити це все на ДАО рівні і дуже круто тепер виглядає
        List<Room> roomList = roomDAO.findAllByRoominessAndHotelCityOrRoominessAndHotelName(countOfGuests,finder,countOfGuests,finder);
        //Фільтрування за цінами
        if(filter.equals("Descending")){
            rooms.addAll(roomList.stream().sorted((o1, o2) -> o2.getPrice()-o1.getPrice()).collect(Collectors.toList()));
        }if(filter.equals("Ascending")){
            rooms.addAll(roomList.stream().sorted((o1, o2) -> o1.getPrice()-o2.getPrice()).collect(Collectors.toList()));
        }
        model.addAttribute("rooms", rooms);
        return "main";
    }
}