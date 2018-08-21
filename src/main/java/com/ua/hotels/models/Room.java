package com.ua.hotels.models;


import com.ua.hotels.models.enums.Type;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Type type;
    private String description;
    private List<String> photos;
    private float price;

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private Hotel hotel;

}
