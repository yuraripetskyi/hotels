package com.ua.hotels.service;

import com.ua.hotels.models.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface HotelService{

    void save(Hotel hotel);

    Optional<Hotel> findById(int id);

    List<Hotel> findAll();

    void deleteById(int id);
}
