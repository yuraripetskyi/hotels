package com.ua.hotels.service;

import com.ua.hotels.models.Adress;
import org.springframework.stereotype.Service;


@Service
public interface AdressService {

    void save(Adress adress);
}
