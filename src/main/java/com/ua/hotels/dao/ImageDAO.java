package com.ua.hotels.dao;

import com.ua.hotels.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDAO  extends JpaRepository<Image,Integer> {
Image findByName(String imagename);
}
