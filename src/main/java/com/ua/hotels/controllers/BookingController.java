package com.ua.hotels.controllers;

import com.ua.hotels.dao.BookDAO;
import com.ua.hotels.dao.GuestDAO;
import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.*;
import com.ua.hotels.models.enums.Role;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.ua.hotels.controllers.MainController.userRole;


@Controller
public class BookingController {

    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private BookDAO bookDAO;

    private String date_from;
    private String date_to;

    @GetMapping("/main")
    private String Mainpage(Model model, @AuthenticationPrincipal Customer user) {
        userRole(user,model);
        return "main";
    }

    @PostMapping("/")
    @ResponseBody
    private List<Room> MainPage(@RequestBody String jsonObj) throws ParseException, org.json.simple.parser.ParseException {
        Object parse = new JSONParser().parse(jsonObj);
        JSONObject jo = (JSONObject)parse;
        String finder = (String)jo.get("finder");
        String  countOfGuests = (String)jo.get("countOfGuests");
        Integer countInt = Integer.parseInt(countOfGuests);
        String from_date = (String)jo.get("from_date");
        String to_date = (String) jo.get("to_date");
        Map<Hotel,List<Room>> obj = new HashMap<>();
        List<Room> rooms = roomDAO.findAllByRoominessAndHotelCityOrRoominessAndHotelName(countInt,finder,countInt,finder);
        List<Room> free_rooms = commpareDates(rooms, from_date, to_date);

        return free_rooms;
    }

    @GetMapping("/book/room/{id}")
    public String bookPage(@PathVariable int id,
                           @AuthenticationPrincipal Customer user,
                           Model model) {
       userRole(user,model);
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
                       ) {
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

        for (Room room : rooms) {
            System.out.println("++++++++++++++++++++");
            System.out.println(room.getId() + " " + room.getBook());
            System.out.println("++++++++++++++++++++");
            List<Book> books = room.getBook();
                for (Book boo : books) {
                    Date book_from = new SimpleDateFormat("MM/dd/yyyy").parse(boo.getDate_from());
                    Date book_to = new SimpleDateFormat("MM/dd/yyyy").parse(boo.getDate_to());
                    System.out.println("++++++++++++++++++++");
                    System.out.println(room.getId() + " " + book_from + " " + book_to);
                    System.out.println("++++++++++++++++++++");
                    if (from.compareTo(book_from) <= 0 || from.compareTo(book_to) <= 0) {
                        deleteRoomFromList(rooms);
                        return rooms;
                    }
                    if (to.compareTo(book_from) <= 0 || to.compareTo(book_to) <= 0) {
                        deleteRoomFromList(rooms);
                        return rooms;
                    }
                    if (to.compareTo(book_from) < 0 || from.compareTo(book_to) > 0)
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
            itr.next();
            itr.remove();
        }
        else{
            itr.remove();
        }
    }
}