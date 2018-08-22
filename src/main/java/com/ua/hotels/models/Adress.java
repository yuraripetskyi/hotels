package com.ua.hotels.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "hotel")
@Entity
public class Adress {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String city;
    private String street;
    private String house;

    public Adress(String city, String street, String house, Hotel hotel) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.hotel = hotel;
    }

    @OneToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    private Hotel hotel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
