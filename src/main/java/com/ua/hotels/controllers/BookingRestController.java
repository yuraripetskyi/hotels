package com.ua.hotels.controllers;

import com.ua.hotels.dao.BookDAO;
import com.ua.hotels.dao.HotelDAO;
import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.Book;
import com.ua.hotels.models.Hotel;
import com.ua.hotels.models.Room;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class BookingRestController {
    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private BookDAO bookDAO;
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
        Map<Hotel,List<Room>>obj = new HashMap<>();
        List<Room> rooms = roomDAO.findAllByRoominessAndHotelCityOrRoominessAndHotelName(countInt,finder,countInt,finder);


//        List<Room> free_rooms = commpareDates(rooms, from_date, to_date);
        return rooms;
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
}
