package com.ua.hotels.dao;

import com.ua.hotels.models.Hotel;
import com.ua.hotels.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Struct;
import java.util.LinkedList;
import java.util.List;

public interface RoomDAO extends JpaRepository<Room,Integer> {
            List<Room>findAllByHotelCityOrHotelName(String finder,String finderr);

    List<Room>findAll();
    //Потрібно шукати за кімнатими, а потім за містом або назвою, бо кімнати відфільтровують зайві запити
    LinkedList<Room> findAllByRoominessAndHotelCityOrRoominessAndHotelName(Integer roominess, String finder, Integer room, String name);
    Room findById(int id);
}
