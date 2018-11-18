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
import java.util.*;
import java.util.stream.Collectors;

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
        List<Room> list = new ArrayList<>();
        Date from = new SimpleDateFormat("MM.dd.yyyy").parse(from_date);
        Date to = new SimpleDateFormat("MM.dd.yyyy").parse(to_date);
        if(from.compareTo(to) >= 0){
            return list;
        }else{
            LinkedList<Room> rooms =  roomDAO.findAllByRoominessAndHotelCityOrRoominessAndHotelName(countInt,finder,countInt,finder);
            return compareDates(rooms, from_date, to_date);}
    }
    @PostMapping("/sortBy")
    private List<Room> sortedRooms(@RequestBody String jsonObj) throws org.json.simple.parser.ParseException, ParseException {
        Object parse = new JSONParser().parse(jsonObj);
        JSONObject jo = (JSONObject)parse;
        String finder = (String)jo.get("finder");
        String  countOfGuests = (String)jo.get("countOfGuests");
        Integer countInt = Integer.parseInt(countOfGuests);
        String from_date = (String)jo.get("from_date");
        String to_date = (String) jo.get("to_date");
        String sortBy = (String) jo.get("sort");
        List<Room> list = new ArrayList<>();
        Date from = new SimpleDateFormat("MM.dd.yyyy").parse(from_date);
        Date to = new SimpleDateFormat("MM.dd.yyyy").parse(to_date);
        if(from.compareTo(to) >= 0){
            return list;
        }else {
            LinkedList<Room> rooms = roomDAO.findAllByRoominessAndHotelCityOrRoominessAndHotelName(countInt, finder, countInt, finder);
            List<Room> free_rooms = compareDates(rooms, from_date, to_date);
            List<Room> sortedRooms = new ArrayList<>();
            if (sortBy.equals("cheap")) {
                sortedRooms = free_rooms.stream().sorted(Comparator.comparing(Room::getPrice)).collect(Collectors.toList());
            }
            if (sortBy.equals("expensive")) {
                sortedRooms = free_rooms.stream().sorted(Comparator.comparing(Room::getPrice).reversed()).collect(Collectors.toList());
            }
            return sortedRooms;
        }
    }
    @PostMapping("/findRoomsForAdmin")
    private List<Book> findBooking(@RequestBody String jsonObj) throws org.json.simple.parser.ParseException {
        Object parse = new JSONParser().parse(jsonObj);
        JSONObject jo = (JSONObject)parse;
        String from = (String)jo.get("date_from");
        String to = (String)jo.get("date_to");
        String string = (String)jo.get("roomId");
        Integer roomId = Integer.valueOf(string);

        Room room = roomDAO.findById(roomId).get();
        List<Book> books = new ArrayList<>();
        for (Book book1 : room.getBook()) {
            if( (from.compareTo(book1.getDate_to()) > 0 || ((to.compareTo(book1.getDate_from()) < 0)) )) {
            }else {
                books.add(book1);
            }
        }
        return books;

    }
    private List<Room> compareDates(List<Room> rooms, String from_date, String to_date) throws ParseException {
        System.out.println(rooms + " " + from_date + " " + to_date);
        Date from = new SimpleDateFormat("MM.dd.yyyy").parse(from_date);
        Date to = new SimpleDateFormat("MM.dd.yyyy").parse(to_date);

        for (Room room : rooms) {
            List<Book> books = room.getBook();
            for (Book boo : books) {
                Date book_from = new SimpleDateFormat("MM.dd.yyyy").parse(boo.getDate_from());
                Date book_to = new SimpleDateFormat("MM.dd.yyyy").parse(boo.getDate_to());
                if( (from.compareTo(book_to) > 0 || ((to.compareTo(book_from) < 0)) )) {
                }else {
                    deleteRoomFromList(rooms);
                    break;
                }
            }
        }
        return rooms;
    }
    private void deleteRoomFromList(List<Room> rooms){
        Iterator<Room> itr = rooms.iterator();
        Room next = itr.next();
        if (itr.hasNext()){
            itr.remove();
        }
        else{
            itr.remove();
        }
    }
}
