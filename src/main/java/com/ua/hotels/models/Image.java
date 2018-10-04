package com.ua.hotels.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ua.hotels.service.ImageService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.File;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @JsonIgnore
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

    public String getImage(){
        return File.separator+"images"+File.separator+name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id &&
                Objects.equals(name, image.name) &&
                Objects.equals(hotel, image.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hotel);
    }

}
