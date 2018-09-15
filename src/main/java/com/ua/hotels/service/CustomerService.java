package com.ua.hotels.service;

import com.ua.hotels.models.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface
CustomerService extends UserDetailsService {

    void save(Customer customer);

    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

    UserDetails loadUserById(int id);

    UserDetails loadByCode(String code);



}
