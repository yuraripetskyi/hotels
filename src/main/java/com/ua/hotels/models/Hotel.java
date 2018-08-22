package com.ua.hotels.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"rooms"})
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
//    private Map<String, String> address;
    private String street;
    private String city;
    private String house;
    private byte stars;
    private String description;
    //    private Contacts contacts;
    private String email;
    private String phone;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
//    private List<String> photos;

    //role have to be admin
    private Customer admin;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "hotel"
    )
    private List<Room> rooms;

    public Hotel(String name, Map<String, String> address, byte stars, String description, List<String> photos, Customer admin, List<Room> rooms) {
        this.name = name;
//        this.address = address;
        this.stars = stars;
        this.description = description;
//        this.contacts = contacts;
//        this.photos = photos;
        this.admin = admin;
        this.rooms = rooms;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Map<String, String> getAddress() {
//        return address;
//    }
//
//    public void setAddress(Map<String, String> address) {
//        this.address = address;
//    }

    public byte getStars() {
        return stars;
    }

    public void setStars(byte stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


//
//    public List<String> getPhotos() {
//        return photos;
//    }
//
//    public void setPhotos(List<String> photos) {
//        this.photos = photos;
//    }

    public Customer getAdmin() {
        return admin;
    }

    public void setAdmin(Customer admin) {
        this.admin = admin;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
