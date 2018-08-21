package com.ua.hotels.dao;

import com.ua.hotels.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerDAO extends JpaRepository<Customer,Integer> {
    Customer findByUsername(String username);

    Customer findByEmail(String email);

    Customer findById(int id);
    Customer findByCode(String code);

    List<Customer> findAllByName(String name);

    List<Customer> findAllBySurname(String surname);

    List<Customer> findAllByNameAndSurname(String name, String surname);
    //Customer deleteByAccountNonExpired(String accountNonExpired);
}
