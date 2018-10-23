package com.ua.hotels.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ua.hotels.models.enums.Stars;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@ToString(exclude = {"rooms","customer","channel","phone","stans"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String city;
    private String street;
    private String email;
    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "hotel"
    )
    private List<Phone> phone;

    private String description;
    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "hotel"
    )
    private List<Stan> stans ;
    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "hotel"
    )
    private List<Room> rooms ;
    @JsonIgnore
    @ManyToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER
    )
    private Customer customer;
    @JsonIgnore
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private Channel channel;

    public Hotel(String name, String city, String street, String email,String description) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.email = email;
        this.description = description;
    }
//    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "hotel"
    )
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Phone> getPhone() {
        return phone;
    }

    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Stan> getStans() {
        return stans;
    }

    public void setStans(List<Stan> stans) {
        this.stans = stans;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
