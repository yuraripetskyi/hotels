package com.ua.hotels.models;

import com.ua.hotels.models.enums.Stars;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"hotel"})
public class Stan {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;
    private Stars stars;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private Hotel hotel;

}
