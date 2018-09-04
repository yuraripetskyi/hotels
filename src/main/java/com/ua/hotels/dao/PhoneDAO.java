package com.ua.hotels.dao;

import com.ua.hotels.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneDAO extends JpaRepository<Phone,Integer> {
}
