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
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class BookingController {

    @Autowired
    private RoomDAO roomDAO;

    @GetMapping("/main")
    private String MainPage(@RequestParam(required = false, defaultValue = "") String finder,
                            @RequestParam(required = false, defaultValue = "") String countOfGuests,
                            @RequestParam(required = false, defaultValue = "") String filter,
                            Model model) {
        List<Room> rooms = new ArrayList<>();
        List<Room> all = roomDAO.findAll();
        // Пошук румів по кількості кімнат
            List<Room> rooms1 = all.stream()
                    .filter(room -> Integer.toString(room.getRoominess()).equals(countOfGuests)).collect(Collectors.toList());
            //Пошук румів по місту чи назві готелю
        List<Room> сityRoom = rooms1.stream()
                //Тут буде помилка якщо назва готелю буде така ж як назва міста, тоді готелі виведе двічі
                .filter(room -> room.getHotel().getCity().equals(finder)).collect(Collectors.toList());
        rooms.addAll(rooms1.stream()
                   .filter(room -> room.getHotel().getName().equals(finder)).collect(Collectors.toList()));
            //Фільтрування за цінами
        if(filter.equals("Descending")){
            rooms.addAll(сityRoom.stream().sorted((o1, o2) -> o2.getPrice()-o1.getPrice()).collect(Collectors.toList()));
        }if(filter.equals("Ascending")){
            rooms.addAll(сityRoom.stream().sorted((o1, o2) -> o1.getPrice()-o2.getPrice()).collect(Collectors.toList()));
        }

        model.addAttribute("rooms", rooms);
        return "main";
    }
}