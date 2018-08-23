package com.ua.hotels.models;


import com.ua.hotels.models.enums.Type;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"hotel"})
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private String description;
    private double price;
    //    private List<String> photos;

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private Hotel hotel;

    public Room(String type, String description, double price) {
        this.type = type;
        this.description = description;
        this.price = price;
    }

    public Room(String type, String description, List<String> photos, double price, Hotel hotel) {
        this.type = type;
        this.description = description;
//        this.photos = photos;
        this.price = price;
        this.hotel = hotel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                Double.compare(room.price, price) == 0 &&
                type == room.type &&
                Objects.equals(description, room.description) &&
//                Objects.equals(photos, room.photos) &&
                Objects.equals(hotel, room.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description, /*photos, */ price, hotel);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String  type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<String> getPhotos() {
//        return photos;
//    }
//
//    public void setPhotos(List<String> photos) {
//        this.photos = photos;
//    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
