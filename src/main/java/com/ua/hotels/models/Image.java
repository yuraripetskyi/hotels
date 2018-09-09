package com.ua.hotels.models;

import com.ua.hotels.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.File;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne(
            cascade =CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    private Hotel hotel;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Image(String name) {
        this.name = name;
    }

    public Image(String name, Hotel hotel) {
        this.name = name;
        this.hotel = hotel;
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
}
