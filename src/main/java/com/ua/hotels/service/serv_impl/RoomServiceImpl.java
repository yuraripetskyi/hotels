package com.ua.hotels.service.serv_impl;

import com.ua.hotels.dao.RoomDAO;
import com.ua.hotels.models.Room;
import com.ua.hotels.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDAO roomDAO;

    @Override
    public void save(Room room) {
        roomDAO.save(room);
    }
}
