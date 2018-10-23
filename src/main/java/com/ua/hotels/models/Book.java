package com.ua.hotels.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@ToString(exclude = {"room", "guest"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

@EqualsAndHashCode
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String  date_from;
    private String date_to;
@JsonIgnore
    @ManyToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    private Room room;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH
    )
    private Guest guest;
    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private Customer customer;

    public Book(String date_from, String date_to) {
        this.date_from = date_from;
        this.date_to = date_to;
    }

    public Book(String date_from, String date_to, Room room, Guest guest) {
        this.date_from = date_from;
        this.date_to = date_to;
        this.room = room;
        this.guest = guest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}


