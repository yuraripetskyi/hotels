package com.ua.hotels.config;

import com.ua.hotels.models.Customer;
import com.ua.hotels.models.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customerServiceImpl")
    UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryConfigurer() {
        return new InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder>();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                AuthenticationProvider provider) throws Exception {

        ( inMemoryConfigurer())
                .withUser("admin")
                .password("{noop}pidoros").roles("ADMIN")
                .authorities("ADMIN")
                .and()
                .configure(auth);
        auth.authenticationProvider(provider);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").hasRole("USER")
//                .antMatchers("/calendar/room/**").hasRole("HOTELADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/hoteladmin/**").hasRole("HOTELADMIN")
                .antMatchers("/hotel/**").hasRole("HOTELADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/unsuccess")
                .successForwardUrl("/success")
                .and()
                .csrf().disable();
    }



    
}
