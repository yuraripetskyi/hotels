package com.ua.hotels.dao;

import com.ua.hotels.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelDAO extends JpaRepository<Hotel,Integer> {

}
