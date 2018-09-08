package com.ua.hotels.models;

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
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "hotel"
    )
    private List<Phone> phone;

    private String description;
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "hotel"
    )
    private List<Stan> stans ;
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "hotel"
    )
    private List<Room> rooms ;
    @ManyToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    private Customer customer;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private Channel channel;


    public void setCustomer(Customer customer) {

        this.customer = customer;
    }
}
