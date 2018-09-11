package com.ua.hotels.models;

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
    private Status status;

    public Room(int price, int roominess, Type type, Status status) {
        this.price = price;
        this.roominess = roominess;
        this.type = type;
        this.status = status;
    }



    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private List<Customer> customers ;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private Hotel hotel;

}
