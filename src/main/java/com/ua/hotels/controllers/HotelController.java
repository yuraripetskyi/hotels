package com.ua.hotels.controllers;

import com.ua.hotels.dao.HotelDAO;
import com.ua.hotels.dao.PhoneDAO;
import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.*;
import com.ua.hotels.models.enums.Role;
import com.ua.hotels.models.enums.Status;
import com.ua.hotels.models.enums.Type;
import com.ua.hotels.models.Customer;
import com.ua.hotels.models.Hotel;
import com.ua.hotels.models.Image;
import com.ua.hotels.models.Phone;
import com.ua.hotels.service.CustomerService;
import com.ua.hotels.service.ImageService;
import com.ua.hotels.utils.CustomerEditor;
import com.ua.hotels.utils.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.ua.hotels.controllers.MainController.userRole;

@Controller
public class HotelController {

    @Autowired
    private Environment environment;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private CustomerEditor customerEditor;

    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private HotelDAO hotelDAO;

    @Autowired
    private PhoneDAO phoneDAO;

    @Autowired
    private RoomDAO roomDAO;

    @GetMapping("/create/hotel")
    public String createHotel(Model model,
                              @AuthenticationPrincipal Customer user)
    {
        userRole(user,model);

        return "createHotel";
    }

    @PostMapping("/save/hotel")
    public String saveHotel(@RequestParam("name") String name
            , @RequestParam("city") String city
            , @RequestParam("street") String street
            , @RequestParam("email") String email
            , @RequestParam("description") String description
            , @RequestParam("phones") String[] phones
            , @RequestParam("prices") String[] prices
            , @RequestParam("rooms") String[] rooms
            , @RequestParam("types") String[] types) {

        Hotel hotel = new Hotel(name, city, street, email, description);
        Customer user = MainController.findActiveUser();
        hotel.setCustomer(user);
        hotelDAO.save(hotel);
        for (String phone : phones) {
            Phone phonec = new Phone(phone);
            phonec.setHotel(hotel);
            phoneDAO.save(phonec);
        }
        for (int i = 0; i < rooms.length; i++) {
            String room = rooms[i];
            String price = prices[i];
            String type = types[i];
            Type mainType = Type.valueOf(type);
            Status stan = Status.STATUS_FREE;
            Room mainRoom = new Room(Integer.parseInt(price), Integer.parseInt(room), mainType, stan);
            mainRoom.setHotel(hotel);
            roomDAO.save(mainRoom);
        }
        return "redirect:/hoteladmin/" + user.getUsername();
    }

    @GetMapping("/hotel/{id}")
    public String hotel(@PathVariable String id,Model model,
                        @AuthenticationPrincipal Customer user) {
        userRole(user,model);

        Hotel hotel = hotelDAO.findById(Integer.parseInt(id)).get();
        model.addAttribute("hotel", hotel);
        model.addAttribute("types", Type.values());
        model.addAttribute("images", hotel.getImages());
        return "hotel";
    }

    @DeleteMapping("/delete/hotel/{id}")
    public String deleteHotel(@PathVariable String id) {
        hotelDAO.delete(hotelDAO.findById(Integer.parseInt(id)).get());
        Customer user = MainController.findActiveUser();
        return "redirect:/hoteladmin/" + user.getUsername();
    }


    @Autowired
    private ImageService imageService;

    @PostMapping("/upload/photos/hotel/{id}")
    public String uploadPhotos(@PathVariable int id,
                               @RequestParam(value = "images") MultipartFile[] files,
                               Model model) throws IOException {
        Hotel hotel = hotelDAO.findById(id).get();
        model.addAttribute("hotel", hotel);
        for (MultipartFile file : files) {
            imageService.createImage(file);
            imageService.save(new Image(file.getOriginalFilename(), hotel));
        }
        return "hotel";
    }

    @GetMapping("/change/hotel/{id}")
    private String changeHotel(@PathVariable int id, Model model, @AuthenticationPrincipal Customer user) {
        userRole(user,model);

        Hotel hotel = hotelDAO.findById(id).get();
        model.addAttribute("hotel", hotel);

        return "changesHotel";
    }

    @PostMapping("/save/changes/hotel/{id}")
    public String saveChangesHotel(@PathVariable int id,
                                   Model model,
                                   @RequestParam String name,
                                   @RequestParam String city,
                                   @RequestParam String street,
                                   @RequestParam String email,
                                   @RequestParam String description,
                                   @RequestParam(value = "phones") String[] phones,
                                   @RequestParam(value = "images") MultipartFile[] files
    ) {
        Hotel hotel = hotelDAO.findById(id).get();

        if (!(name.equals(""))) {
            hotel.setName(name);
        }
        if (!(city.equals(""))) {
            hotel.setCity(city);
        }
        if (!(street.equals(""))) {
            hotel.setStreet(street);
        }
        if (!(email.equals(""))) {
            hotel.setEmail(email);
        }
        if (!(description.equals(""))) {
            hotel.setDescription(description);
        }
        if (!(name.equals("")))
            if (!(phones.equals(""))) {
                for (String phone : phones) {
                    Phone phonec = new Phone(phone);
                    phonec.setHotel(hotel);
                }
//        if(files != null){
//            for (MultipartFile file : files) {
//                imageService.createImage(file);
//                imageService.save(new Image(file.getOriginalFilename(),hotel));
//            }
//        }
            }
        hotelDAO.save(hotel);
        model.addAttribute("hotel", hotel);
        return "hotel";
    }

    @GetMapping("/change/room/{id}")
    private String changeRoom(@PathVariable int id, Model model, @AuthenticationPrincipal Customer user
    ) {
       userRole(user,model);

        Room room = roomDAO.findById(id).get();
        model.addAttribute("hotel", room.getHotel());
        model.addAttribute("room", room);
        return "changesRoom";
    }

    @PostMapping("/update/room/{id}")
    public String updateRoom(@PathVariable int id,
                             Model model,
                             @RequestParam int prices,
                             @RequestParam int rooms,
                             @RequestParam Type type) {

        Room room = roomDAO.findById(id).get();
        Hotel hotel = room.getHotel();
        room.setPrice(prices);
        room.setRoominess(rooms);
        room.setType(type);
        roomDAO.save(room);
        model.addAttribute("hotel", hotel);

        return "hotel";
    }
//    private void userRole(Customer user, Model model){
//        if(user!=null){
//            model.addAttribute("user", user);
//        }else {
//            model.addAttribute("user", null);
//        }
//        model.addAttribute("admin_role", Role.ROLE_ADMIN);
//        model.addAttribute("user_role", Role.ROLE_USER);
//        model.addAttribute("hoteladmin_role", Role.ROLE_HOTELADMIN);
//
//    }
    @PostMapping("/add/room")
    public String addRoom(
            @RequestParam("hotelId") Hotel hotel,
            @RequestParam int price,
            @RequestParam int roominess,
            @RequestParam String type,
            @RequestParam String status,
            Model model){

        Room room = new Room(price, roominess, Type.valueOf(type), Status.valueOf(status));
            room.setHotel(hotel);
        roomDAO.save(room);
        return "redirect:/hotel/" + hotel.getId();
    }

}

