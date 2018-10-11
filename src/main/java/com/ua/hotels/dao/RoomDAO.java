package com.ua.hotels.dao;

import com.ua.hotels.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Struct;
import java.util.LinkedList;
import java.util.List;

public interface RoomDAO extends JpaRepository<Room,Integer> {
    //Потрібно шукати за кімнатими, а потім за містом або назвою, бо кімнати відфільтровують зайві запити
    LinkedList<Room> findAllByRoominessAndHotelCityOrRoominessAndHotelName(Integer roominess, String finder, Integer room, String name);
}
