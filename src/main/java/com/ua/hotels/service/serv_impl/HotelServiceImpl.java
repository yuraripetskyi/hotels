package com.ua.hotels.service.serv_impl;

import com.ua.hotels.dao.HotelDAO;
import com.ua.hotels.models.Hotel;
import com.ua.hotels.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;

public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelDAO hotelDAO;

    @Override
    public void save(Hotel hotel) {
        hotelDAO.save(hotel);
    }
}
