package com.ua.hotels.dao;

import com.ua.hotels.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDAO extends JpaRepository<Contact, Integer> {
}
