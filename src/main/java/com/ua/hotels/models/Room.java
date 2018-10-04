package com.ua.hotels.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ua.hotels.models.enums.Status;
import com.ua.hotels.models.enums.Type;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString(exclude = {"customers","hotel"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int price;
    private int roominess;
    private Type type;

    public Room(int price, int roominess, Type type, Status status) {
        this.price = price;
        this.roominess = roominess;
        this.type = type;

    }
    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private List<Customer> customers ;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH
    )
    private Hotel hotel;
    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "room"
    )
    private List<Book> book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRoominess() {
        return roominess;
    }

    public void setRoominess(int roominess) {
        this.roominess = roominess;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
