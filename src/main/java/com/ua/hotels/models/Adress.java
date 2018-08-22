package com.ua.hotels.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "hotel")
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String house;
    private String street;
    private String city;

    @OneToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    private Hotel hotel;
}
