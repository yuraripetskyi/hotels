package com.ua.hotels.controllers;

import com.ua.hotels.dao.BookDAO;
import com.ua.hotels.dao.GuestDAO;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class BookingController {

    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private BookDAO bookDAO;

    /*@GetMapping("/main")
    private String Mainpage() {
        return "main";
    }*/


    @GetMapping("/book/room/{id}")
    public String bookPage(@PathVariable int id,
                           Model model) {
        Room room = roomDAO.findById(id).get();
        model.addAttribute("room", room);
        model.addAttribute("hotel", room.getHotel());
        return "book";
    }


    @Autowired
    private GuestDAO guestDAO;
    @PostMapping("/book/room/{id}")
    public String book(@PathVariable int id,
                       @RequestParam String from_date,
                       @RequestParam String to_date,
                       @RequestParam String name,
                       @RequestParam String surname,
                       @RequestParam String email
                       /*@RequestParam */) {
        Customer activeUser = MainController.findActiveUser();
        Room room = roomDAO.findById(id).get();
        Guest guest = new Guest(name,surname,email);
        guestDAO.save(guest);
        Book book = new Book(from_date,to_date,room,guest);
        if(activeUser != null){
            book.setCustomer(activeUser);
        }
        bookDAO.save(book);

        return "main";
    }
    private void deleteRoomFromList(List<Room> rooms){
        Iterator itr = rooms.iterator();
        if (itr.hasNext()) {
            itr.next();
            itr.remove();
        }else{
            itr.remove();
        }
    }
}