package com.ua.hotels.dao;

import com.ua.hotels.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelDAO extends JpaRepository<Hotel,Integer> {
    List<Hotel> findAllByCityOrName(String finder,String finderr);
}
