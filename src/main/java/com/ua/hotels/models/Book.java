package com.ua.hotels.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@ToString(exclude = {"room", "guest"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String  date_from;
    private String date_to;

    @ManyToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    private Room room;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private Guest guest;

}


