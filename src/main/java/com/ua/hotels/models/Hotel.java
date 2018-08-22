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
    private byte stars;
    private String description;

    public Hotel() {
    }

    @OneToOne(
                fetch = FetchType.LAZY,
cascade = CascadeType.REFRESH
        )
        private Contact contacts;

        @OneToOne(
              fetch = FetchType.LAZY,
              cascade = CascadeType.REFRESH
        )
private Adress adress;


    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }





//    private List<String> photos;

    //role have to be admin


//    private Customer admin;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "hotel"
    )
    private List<Room> rooms;

    public Hotel(String name, byte stars, String description, Contact contacts, Adress adress, Customer admin, List<Room> rooms) {
        this.name = name;
        this.stars = stars;
        this.description = description;
        this.contacts = contacts;
        this.adress = adress;
//        this.admin = admin;
//        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

    public Contact getContacts() {
        return contacts;
    }

    public void setContacts(Contact contacts) {
        this.contacts = contacts;
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
