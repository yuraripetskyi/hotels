package com.ua.hotels.service.serv_impl;

import com.ua.hotels.dao.HotelDAO;
import com.ua.hotels.models.Hotel;
import com.ua.hotels.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelDAO hotelDAO;

    @Override
    public void save(Hotel hotel) {
        hotelDAO.save(hotel);
    }

    @Override
    public Optional<Hotel> findById(int id) {
        return hotelDAO.findById(id);
    }
    @Override
    public List<Hotel> findAll() {
        return hotelDAO.findAll();
    }

    @Override
    public void deleteById(int id) {
        hotelDAO.deleteById(id);
    }
}
