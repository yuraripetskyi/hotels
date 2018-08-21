package com.ua.hotels.models;

import com.ua.hotels.models.enums.Stars;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Map<String, String> address;
    private Stars stars;
    private String description;
    private Contacts contacts;
    private List<String> photos;

    private Admin admin;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "hotel"
    )
    private List<Room> rooms;

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", stars=" + stars +
                ", description='" + description + '\'' +
                ", contacts=" + contacts +
                ", photos=" + photos +
                ", admin=" + admin +
                ", rooms=" + rooms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return id == hotel.id &&
                Objects.equals(name, hotel.name) &&
                Objects.equals(address, hotel.address) &&
                Objects.equals(stars, hotel.stars) &&
                Objects.equals(description, hotel.description) &&
                Objects.equals(contacts, hotel.contacts) &&
                Objects.equals(photos, hotel.photos) &&
                Objects.equals(admin, hotel.admin) &&
                Objects.equals(rooms, hotel.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, stars, description, contacts, photos, admin, rooms);
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

    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }

    public Stars getStars() {
        return stars;
    }

    public void setStars(Stars stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
