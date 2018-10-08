package com.ua.hotels.controllers;

import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.Book;
import com.ua.hotels.models.Room;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
public class BookingRestController {
    @Autowired
    private RoomDAO roomDAO;

    @PostMapping("/")
    private List<Room> MainPage(@RequestBody String jsonObj) throws ParseException, org.json.simple.parser.ParseException {
        Object parse = new JSONParser().parse(jsonObj);
        JSONObject jo = (JSONObject)parse;
        String finder = (String)jo.get("finder");
        String  countOfGuests = (String)jo.get("countOfGuests");
        Integer countInt = Integer.parseInt(countOfGuests);
        String from_date = (String)jo.get("from_date");
        String to_date = (String) jo.get("to_date");

        List<Room> rooms = roomDAO.findAllByRoominessAndHotelCityOrRoominessAndHotelName(countInt,finder,countInt,finder);
        List<Room> free_rooms = compareDates(rooms, from_date, to_date);

        return free_rooms;
    }

    private List<Room> compareDates(List<Room> rooms, String from_date, String to_date) throws ParseException {

        Date from = new SimpleDateFormat("MM.dd.yyyy").parse(from_date);
        Date to = new SimpleDateFormat("MM.dd.yyyy").parse(to_date);
        System.out.println("++++++++++++++");
        System.out.println( from + " "  +to);
        System.out.println("++++++++++++++");
        for (Room room : rooms) {
            List<Book> books = room.getBook();
            System.out.println("++++++++++++++");
            System.out.println(room.getId() + " " + books);
            System.out.println("++++++++++++++");
            for (Book boo : books) {

                Date book_from = new SimpleDateFormat("MM.dd.yyyy").parse(boo.getDate_from());
                Date book_to = new SimpleDateFormat("MM.dd.yyyy").parse(boo.getDate_to());
                System.out.println("====================");
                System.out.println(book_from + " " + book_to);
                System.out.println("====================");
                if (from.compareTo(book_from) >= 0 && from.compareTo(book_to) <= 0) {
                    System.out.println("++++++++++++++");
                    System.out.println("true");
                    System.out.println("++++++++++++++");
                    deleteRoomFromList(rooms);
                    break;
                }
                if (to.compareTo(book_from) >= 0 && to.compareTo(book_to) <= 0) {
                    System.out.println("++++++++++++++");
                    System.out.println("true");
                    System.out.println("++++++++++++++");
                    deleteRoomFromList(rooms);
                    break;
                }
                if (to.compareTo(book_from) <= 0 && from.compareTo(book_to) >= 0){
                    System.out.println("++++++++++++++");
                    System.out.println("true");
                    System.out.println("++++++++++++++");
                    deleteRoomFromList(rooms);
                    break;
                }

            }
        }
        return rooms;
    }
    private void deleteRoomFromList(List<Room> rooms){
        Iterator itr = rooms.iterator();
        if (itr.hasNext()) {
           itr.next();
            itr.next(); // sho za pizda?????
            itr.remove();
        }
        else{
            itr.remove();
        }
    }
}
