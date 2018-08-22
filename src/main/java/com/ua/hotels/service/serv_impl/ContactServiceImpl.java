package com.ua.hotels.service.serv_impl;

import com.ua.hotels.dao.ContactDAO;
import com.ua.hotels.models.Contact;
import com.ua.hotels.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDAO contactDAO;
    @Override
    public void save(Contact contact) {
        contactDAO.save(contact);
    }
}
