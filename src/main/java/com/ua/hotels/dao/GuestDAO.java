package com.ua.hotels.dao;

import com.ua.hotels.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestDAO extends JpaRepository<Guest, Integer> {
}
