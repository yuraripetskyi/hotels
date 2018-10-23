package com.ua.hotels.dao;

import com.ua.hotels.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDAO extends JpaRepository<Book, Integer > {

}
