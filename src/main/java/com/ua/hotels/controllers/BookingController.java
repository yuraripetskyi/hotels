package com.ua.hotels.controllers;

import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.Book;
import com.ua.hotels.models.Customer;
import com.ua.hotels.models.Guest;
import com.ua.hotels.models.Room;
import com.ua.hotels.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
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
        List<Room> roomList = roomDAO.findAllByRoominessAndHotelCityOrRoominessAndHotelName(countOfGuests,finder,countOfGuests,finder);
        rooms.addAll(filterByPrice(filter, roomList));
        model.addAttribute("rooms", rooms);
        return "main";
    }

    @GetMapping("/book/room/{id}")
    public String bookPage(@PathVariable int id,
                           Model model){
        Room room = roomDAO.findById(id).get();
        model.addAttribute("room", room);
        model.addAttribute("hotel", room.getHotel());
        return "book";
    }

    @PostMapping("/book/room/{id}")
    public String book(@PathVariable int id,
                       @RequestBody Guest guest){
        Customer activeUser = MainController.findActiveUser();
        Room room = roomDAO.findById(id).get();
        Book book = new Book();
        book.setCustomer(activeUser);
        book.setRoom(room);
        book.setGuest(guest);
        room.setStatus(Status.STATUS_BUSY);
        roomDAO.save(room);
        return "user";
    }


    private List<Room> filterByPrice(String filter, List<Room> roomList){
        List<Room> rooms = new ArrayList<>();
        if(filter.equals("Descending")){
            rooms.addAll(roomList.stream().sorted((o1, o2) -> o2.getPrice()-o1.getPrice()).collect(Collectors.toList()));
        }if(filter.equals("Ascending")){
            rooms.addAll(roomList.stream().sorted((o1, o2) -> o1.getPrice()-o2.getPrice()).collect(Collectors.toList()));
        }
        return rooms;
    }
}