package com.ua.hotels.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;



@AllArgsConstructor
@ToString(exclude = {"rooms","contacts","adress"})
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String city;
    private String street;
    private String house;
    private String email;
    private String phone;
    private byte stars;
    private String description;

    public Hotel(String name, String city, String street, String house, String email, String phone, byte stars, String description) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.house = house;
        this.email = email;
        this.phone = phone;
        this.stars = stars;
        this.description = description;
    }
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "hotel"
    )
    private List<Room> rooms;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private Customer admin;

    public Customer getAdmin() {
        return admin;
    }

    public void setAdmin(Customer admin) {
        this.admin = admin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public Hotel() {
    }

//    @OneToOne(
//                fetch = FetchType.LAZY,
//cascade = CascadeType.REFRESH
//        )
//        private Contact contacts;
//
//        @OneToOne(
//              fetch = FetchType.LAZY,
//              cascade = CascadeType.REFRESH
//        )
//private Adress adress;


//    public Adress getAdress() {
//        return adress;
//    }
//
//    public void setAdress(Adress adress) {
//        this.adress = adress;
//    }

//    private List<String> photos;

    //role have to be admin


//    private Customer admin;



    public Hotel(String name, byte stars, String description, Contact contacts, Adress adress, Customer admin, List<Room> rooms) {
        this.name = name;
        this.stars = stars;
        this.description = description;
//        this.contacts = contacts;
//        this.adress = adress;
//        this.admin = admin;
//        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

//    public Contact getContacts() {
//        return contacts;
//    }
//
//    public void setContacts(Contact contacts) {
//        this.contacts = contacts;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


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



//    public Customer getAdmin() {
//        return admin;
//    }
//
//    public void setAdmin(Customer admin) {
//        this.admin = admin;
//    }

//    public List<Room> getRooms() {
//        return rooms;
//    }
//
//    public void setRooms(List<Room> rooms) {
//        this.rooms = rooms;
//    }
}
