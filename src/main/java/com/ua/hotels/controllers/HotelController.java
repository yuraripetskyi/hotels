package com.ua.hotels.controllers;

import com.ua.hotels.dao.HotelDAO;
import com.ua.hotels.dao.ImageDAO;
import com.ua.hotels.dao.PhoneDAO;
import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.*;
import com.ua.hotels.models.enums.Status;
import com.ua.hotels.models.enums.Type;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private ImageDAO imageDAO;
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

    @PostMapping("/create/hotel")
    public String saveHotel(@RequestParam("name") String name
            , @RequestParam("city") String city
            , @RequestParam("street") String street
            , @RequestParam("email") String email
            , @RequestParam("description") String description
            , @RequestParam("phones") String[] phones
            , @RequestParam("prices") String[] prices
            , @RequestParam("rooms") String[] rooms
            , @RequestParam("types") String[] types,@AuthenticationPrincipal Customer user) {

        Hotel hotel = new Hotel(name, city, street, email, description);
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
        return "redirect:/hoteladmin";
    }

    @GetMapping("/hotel/{id}")
    public String hotel(@PathVariable String id,Model model,
                        @AuthenticationPrincipal Customer user) {
        userRole(user,model);

        Hotel hotel = hotelDAO.findById(Integer.parseInt(id)).get();
        List<Room> rooms = hotel.getRooms();
        List<Book> allBooks = new ArrayList<>();
        for (Room room: rooms){
            allBooks.addAll(room.getBook());
        }
        model.addAttribute("books", allBooks);
        model.addAttribute("hotel", hotel);
        model.addAttribute("econom", Type.TYPE_ECONOM);
         model.addAttribute("standart", Type.TYPE_STANDART);
         model.addAttribute("luxe", Type.TYPE_LUXE);
        model.addAttribute("types", Type.values());
        if(!hotel.getImages().isEmpty()) {
            model.addAttribute("images", hotel.getImages());
        }else {
            model.addAttribute("image", null);
        }
        return "hotel";
    }

    @DeleteMapping("/delete/hotel/{id}")
    public String deleteHotel(@PathVariable String id) {
        hotelDAO.delete(hotelDAO.findById(Integer.parseInt(id)).get());
        Customer user = MainController.findActiveUser();
        return "redirect:/hoteladmin";
    }


    @Autowired
    private ImageService imageService;

    @PostMapping("/upload/photos/hotel/{id}")
    public String uploadPhotos(@PathVariable int id,
                               @RequestParam(value = "images") MultipartFile[] files,
                               Model model) throws IOException {
        Hotel hotel = hotelDAO.findById(id).get();
        for (MultipartFile file : files) {
            imageService.createImage(file);
            imageService.save(new Image(file.getOriginalFilename(), hotel));
        }
        Hotel hotelzzz = hotelDAO.findById(id).get();
        model.addAttribute("hotel", hotelzzz);
        return "redirect:/hotel/"+id;
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
            }
        hotelDAO.save(hotel);
        model.addAttribute("hotel", hotel);
        return "hotel";
    }

    @GetMapping("/change/room/{id}")
    private String changeRoom(@PathVariable int id, Model model, @AuthenticationPrincipal Customer user
    ) {
       userRole(user,model);

        Room room = roomDAO.findById(id);
        model.addAttribute("hotel", room.getHotel());
        model.addAttribute("room", room);
        return "changesRoom";
    }
    @GetMapping("/delete/photo/{imageId}")
    private String deleteImage(@PathVariable String imageId){
        int imgId = Integer.parseInt(imageId);
        Image image = imageDAO.findById(imgId).get();
        File file = new File(ImageService.UPLOAD_PATH + image.getName());
        file.delete();
        imageDAO.deleteById(Integer.parseInt(imageId));
        return "redirect:/hoteladmin";
    }
    @GetMapping("/calendar/room/{id}")
    private String checkBookingRoom(@PathVariable int id, Model model, @AuthenticationPrincipal Customer user
    ) {
        userRole(user,model);
        Room room = roomDAO.findById(id);
        model.addAttribute("bookings", room.getBook());
        model.addAttribute("room", room);
        return "calendar";
    }
    @PostMapping("/update/room/{id}")
    public String updateRoom(@PathVariable int id,
                             Model model,
                             @RequestParam int prices,
                             @RequestParam int rooms,
                             @RequestParam Type type) {

        Room room = roomDAO.findById(id);
        Hotel hotel = room.getHotel();
        room.setPrice(prices);
        room.setRoominess(rooms);
        room.setType(type);
        roomDAO.save(room);
        model.addAttribute("hotel", hotel);

        return "hotel";
    }

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

