package com.ua.hotels.controllers;

import com.ua.hotels.dao.BookDAO;
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

        model.addAttribute("rooms", free_rooms);
        return "main";
    }

    @GetMapping("/book/room/{id}")
    public String bookPage(@PathVariable int id,
                           Model model) {
        Room room = roomDAO.findById(id).get();
        model.addAttribute("room", room);
        model.addAttribute("hotel", room.getHotel());
        return "book";
    }


    @PostMapping("/book/room/{id}")
    public String book(@PathVariable int id,
                       @RequestBody Guest guest) {
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


    private List<Room> commpareDates(List<Room> rooms, String from_date, String to_date) throws ParseException {

        Date from = new SimpleDateFormat("MM/dd/yyyy").parse(from_date);
        Date to = new SimpleDateFormat("MM/dd/yyyy").parse(to_date);

        List<Book> book = bookDAO.findAll();

        for (Room room : rooms) {
            List<Book> books = room.getBook();
            for (Book boo : books) {

                Date book_from = new SimpleDateFormat("MM/dd/yyyy").parse(boo.getDate_from());
                Date book_to = new SimpleDateFormat("MM/dd/yyyy").parse(boo.getDate_to());
                Iterator itr = rooms.iterator();
                if (from.compareTo(book_from) > 0 && from.compareTo(book_to) < 0) {
                    while (itr.hasNext()) {
                        itr.remove();
                    }
                }
                if (to.compareTo(book_from) > 0 && to.compareTo(book_to) < 0) {
                    while (itr.hasNext()) {
                        itr.remove();
                    }
                }
                if (to.compareTo(book_from) < 0 && from.compareTo(book_to) > 0)
                    while (itr.hasNext()) {
                        itr.remove();
                    }
            }
        }


        return rooms;
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