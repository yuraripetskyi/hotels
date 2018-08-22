package com.ua.hotels.service.serv_impl;

import com.ua.hotels.dao.AdressDAO;
import com.ua.hotels.models.Adress;
import com.ua.hotels.service.AdressService;
import org.springframework.beans.factory.annotation.Autowired;

public class AdressServiceImpl implements AdressService {

    @Autowired
    private AdressDAO adressDAO;

    @Override
    public void save(Adress adress) {

    }
}
