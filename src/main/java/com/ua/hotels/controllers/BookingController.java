package com.ua.hotels.controllers;

import com.ua.hotels.dao.BookDAO;
import com.ua.hotels.dao.GuestDAO;
import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.Book;
import com.ua.hotels.models.Customer;
import com.ua.hotels.models.Guest;
import com.ua.hotels.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;

import static com.ua.hotels.controllers.MainController.userRole;


@Controller
public class BookingController {

    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private BookDAO bookDAO;

    @GetMapping("/book/room/{id}/{date_from}/{date_to}")
    public String bookPage(@PathVariable int id,
                           @PathVariable String date_from,
                           @PathVariable String date_to,
                           @AuthenticationPrincipal Customer user,
                           Model model) {
        userRole(user,model);
        Room room = roomDAO.findById(id);
        model.addAttribute("room", room);
        model.addAttribute("hotel", room.getHotel());
        model.addAttribute("from", date_from);
        model.addAttribute("to", date_to);
        return "book";
    }
    @GetMapping("/book/delete/{bookId}/room/{roomId}")
    public String deleteBook(@PathVariable int bookId,
                             @PathVariable int roomId){
        bookDAO.deleteById(bookId);
        return "redirect:/calendar/room/"+roomId;
    }
    @Autowired
    private GuestDAO guestDAO;
    @Autowired
    LoginForgetController loginForgetController;
    @PostMapping("/book/room/{id}")
    public String book(@PathVariable int id,
                       @RequestParam String from_date,
                       @RequestParam String to_date,
                       @RequestParam String name,
                       @RequestParam String surname,
                       @RequestParam String email,
                       @AuthenticationPrincipal Customer activeUser,
                       Model model
                       ) throws MessagingException {
        Room room = roomDAO.findById(id);
        Guest guest = new Guest(name,surname,email);
        guestDAO.save(guest);
        Book book = new Book(from_date,to_date,room,guest);
        if(activeUser != null){
            book.setCustomer(activeUser);
        }
        String text = "Your booking dates : " + from_date + "/" + to_date + " Hotel name : " + room.getHotel().getName() + " City : " + room.getHotel().getCity() + "<br/>" +
                " Type of hotel room : " + room.getType() + "<br/>"+
                "<a href='http://localhost:8080/'>Hotelzzz official</a>";
        loginForgetController.sendMail(email,"Hotelzzz",text);
        bookDAO.save(book);
        userRole(activeUser,model);
        model.addAttribute("date_from",from_date);
        model.addAttribute("date_to",to_date);
        model.addAttribute("room",room);
        return "success";
    }


    @GetMapping("/book/remove/{id}")
    public String removeBook(@PathVariable int id){
        Book book = bookDAO.findById(id).get();
        bookDAO.delete(book);
        return "redirect:/user";
    }
}