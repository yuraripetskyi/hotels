package com.ua.hotels.dao;

import com.ua.hotels.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDAO extends JpaRepository<Room,Integer> {
}
