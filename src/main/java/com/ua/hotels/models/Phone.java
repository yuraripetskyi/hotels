package com.ua.hotels.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String number;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private Hotel hotel;
}
