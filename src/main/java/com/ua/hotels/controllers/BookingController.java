package com.ua.hotels.controllers;

import com.ua.hotels.dao.BookDAO;
import com.ua.hotels.dao.GuestDAO;
import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.Book;
import com.ua.hotels.models.Customer;
import com.ua.hotels.models.Guest;
import com.ua.hotels.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private String date_from;
    private String date_to;

    @GetMapping("/main")
    private String Mainpage() {
        return "main";
    }

    @PostMapping("/main")
    private String MainPage(@RequestParam(required = false, defaultValue = "") String finder,
                            @RequestParam(required = false, defaultValue = "") Integer countOfGuests,
                            @RequestParam(required = false, defaultValue = "") String filter,
                            @RequestParam(required = false, defaultValue = "") String from_date,
                            @RequestParam(required = false, defaultValue = "") String to_date,
                            Model model) throws ParseException {
        List<Room> rooms = new ArrayList<>();
        List<Room> roomList = roomDAO.findAllByRoominessAndHotelCityOrRoominessAndHotelName(countOfGuests, finder, countOfGuests, finder);
        rooms.addAll(filterByPrice(filter, roomList));
        List<Room> free_rooms = commpareDates(rooms, from_date, to_date);
        date_from = from_date;
        date_to = to_date;
        model.addAttribute("rooms", free_rooms);
        return "main";
    }

    @GetMapping("/book/room/{id}")
    public String bookPage(@PathVariable int id,
                           Model model) {
        Room room = roomDAO.findById(id).get();
        model.addAttribute("room", room);
        model.addAttribute("hotel", room.getHotel());
        model.addAttribute("from", date_from);
        model.addAttribute("to", date_to);
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


    private List<Room> commpareDates(List<Room> rooms, String from_date, String to_date) throws ParseException {

        Date from = new SimpleDateFormat("MM/dd/yyyy").parse(from_date);
        Date to = new SimpleDateFormat("MM/dd/yyyy").parse(to_date);

        List<Book> book = bookDAO.findAll();
        for (Room room : rooms) {
            List<Book> books = room.getBook();
            for (Book boo : books) {

                Date book_from = new SimpleDateFormat("MM/dd/yyyy").parse(boo.getDate_from());
                Date book_to = new SimpleDateFormat("MM/dd/yyyy").parse(boo.getDate_to());

                if (from.compareTo(book_from) > 0 && from.compareTo(book_to) < 0) {
                   deleteRoomFromList(rooms);
                    return rooms;
                }
                if (to.compareTo(book_from) > 0 && to.compareTo(book_to) < 0) {
                    deleteRoomFromList(rooms);
                    return rooms;
                }
                if (to.compareTo(book_from) < 0 && from.compareTo(book_to) > 0)
                    deleteRoomFromList(rooms);
                    return rooms;
            }
        }
        return rooms;
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