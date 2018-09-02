package com.ua.hotels.controllers;

import com.ua.hotels.models.Customer;
import com.ua.hotels.service.CustomerService;
import com.ua.hotels.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Controller
public class LoginForgetController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private static JavaMailSender sender;


    public static void sendMail(String email, String subject, String text) throws javax.mail.MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setText(text, true);
        helper.setSubject(subject);
        helper.setTo(email);
        sender.send(mimeMessage);
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code) {
        Customer user = (Customer) customerService.loadByCode(code);
        user.setEnabled(true);
        customerService.save(user);
        return "login";
    }

    @GetMapping("/login/forgotten")
    public String loginforgot(@RequestParam String email) throws MessagingException {

        Customer user = (Customer) customerService.loadUserByEmail(email);
        String subject = "Hotels - Login";
        user.setCode(UUID.randomUUID().toString());
        customerService.save(user);
        String text = "Your login is: " + user.getUsername() + " <br> Login: <a href='http://localhost:8080/login'>to login</a>";
        sendMail(email, subject, text);
        return "registr";
    }

    @GetMapping("/password/forgotten")
    public String restorePassword(@RequestParam String email) throws MessagingException {
        Customer user = (Customer) customerService.loadUserByEmail(email);
        String subject = "Change password";
        user.setCode(UUID.randomUUID().toString());
        customerService.save(user);
        String text = "Go to the link, to activate your account : <a href='http://localhost:8080/change_password/" + user.getCode() + "'>to change password!</a>";
        sendMail(email, subject, text);
        return "registr";
    }

    @GetMapping("/change_password/{code}")
    public String change_password(@PathVariable String code, Model model) {
        Customer customer = (Customer) customerService.loadByCode(code);
        model.addAttribute("customer", customer);
        return "/changepassword";
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/new_password/{id}")
    public String newPassword(@PathVariable int id,
                              @RequestParam String password1, @RequestParam String password2) {
        Customer customer = (Customer) customerService.loadUserById(id);
        if (password1.equals(password2)) {

            customer.setPassword(passwordEncoder.encode(password1));
            customerService.save(customer);
            return "login";
        } else {
            return "/change_password/" + customer.getCode();
        }
    }
}
