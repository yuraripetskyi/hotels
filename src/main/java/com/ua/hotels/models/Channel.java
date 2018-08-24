package com.ua.hotels.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"customers","hotel"})
@Getter
@Setter
@Builder
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    private List<Customer> customers ;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "channel"
    )
    private Hotel hotel;


}
