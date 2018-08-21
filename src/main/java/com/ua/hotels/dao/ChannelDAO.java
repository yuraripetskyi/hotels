package com.ua.hotels.dao;

import com.ua.hotels.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelDAO extends JpaRepository<Channel,Integer> {
    String findByName(String name);
}
