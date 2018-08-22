package com.ua.hotels.service;

import com.ua.hotels.models.Contact;
import org.springframework.stereotype.Service;


@Service
public interface ContactService {

    void save(Contact contact);
}
