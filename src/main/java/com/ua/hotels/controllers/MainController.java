package com.ua.hotels.controllers;

import com.ua.hotels.models.Customer;
import com.ua.hotels.models.Role;
import com.ua.hotels.service.CustomerService;
import com.ua.hotels.service.CustomerServiceImpl;
import com.ua.hotels.utils.CustomerEditor;
import com.ua.hotels.utils.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@PropertySource("classpath:validation.properties")
public class MainController {

    @Autowired
    private Environment environment;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private CustomerEditor customerEditor;

    @Autowired
    private CustomerValidator customerValidator;

    @GetMapping("/")
    public String index(Model model){
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken) ){
            Customer user = (Customer)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user",user);
            //return "success";
            Role role = user.getRole();
            if (role.equals(Role.ROLE_USER)){
                return "redirect:/user/"+user.getUsername();
            }
            else{
                return "redirect:/admin/"+user.getUsername();
            }
        }else {
            return "index";
        }
    }

    @PostMapping("/success")
    public String success(Model model){
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken) ){
            Customer user = (Customer)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user",user);
            Role role = user.getRole();
            if (role.equals(Role.ROLE_USER)){
                return "redirect:/user/"+user.getUsername();
            }
            else{
                return "redirect:/admin/"+user.getUsername();
            }
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(Customer customer){
        System.out.println(customer.getImage());

        if(customer.isEnabled()){
            return "user";}else {
            return "login";
        }

    }
    @GetMapping("/user/{username}")
    public String user(@PathVariable String username,Model model){
        Customer user =(Customer) customerServiceImpl.loadUserByUsername(username);


        model.addAttribute("user",user);
        return "user";
    }

    @GetMapping("/admin/{username}")
    public String admin(@PathVariable String username,Model model){
        Customer user =(Customer) customerServiceImpl.loadUserByUsername(username);
        model.addAttribute("user",user);
        return "admin";
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @PostMapping("/save")
    public String save(Customer customer , BindingResult result , Model model) throws javax.mail.MessagingException {
        customerValidator.validate(customer,result);
        if (result.hasErrors()) {
            String errors = "";
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError error : allErrors) {
                errors += " " + environment.getProperty(error.getCode());
            }
            model.addAttribute("error", errors);
            return "index";
        }
        customerEditor.setValue(customer);
        customer.setCode(UUID.randomUUID().toString());
customer.setImage(System.getProperty("user.dir")+ File.separator
        +"src"+File.separator+
        "main"+File.separator+
        "resources"+File.separator+
        "static" +File.separator+
        "none.jpg"
);



        customerService.save(customer);

        String text = "Go to the link, to activate your account : <a href='http://localhost:8080/activate/"+ customer.getCode() +"'>Activate</a>";
String subject = "Activate account";



        sendMail(customer.getEmail(), subject , text);


        return "registr";
    }



    private void sendMail(String email, String subject, String text) throws javax.mail.MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);


        helper.setText(text,true);
        helper.setSubject(subject);
        helper.setTo(email);
        sender.send(mimeMessage);

    }
    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code){

        Customer user = (Customer) customerService.loadByCode(code);
        user.setEnabled(true);
        customerService.save(user);
        return "login";
    }

    @GetMapping("/loginresetsend")
    public String  restorePassword(@RequestParam String email) throws MessagingException {

     Customer user = (Customer) customerService.loadUserByEmail(email);
     String subject = "Change password";
        user.setCode(UUID.randomUUID().toString());
        customerService.save(user);
     String text =  "Go to the link, to activate your account : <a href='http://localhost:8080/change_password/"+ user.getCode() +"'>to change password!</a>";
     sendMail(email,subject,text);
     return "registr";
    }

    @GetMapping("/change_password/{code}")
    public String change_password(@PathVariable String code , Model model){
        Customer customer = (Customer) customerService.loadByCode(code);
        model.addAttribute("customer",customer);
        return "/changepassword";
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/new_password/{id}")
    public String newPassword(@PathVariable int id,
            @RequestParam String password1, @RequestParam String password2){
        Customer customer = (Customer) customerService.loadUserById(id);

        System.out.println("------------");
        System.out.println(customer);
        System.out.println("----------------");
        if(password1.equals(password2)){

            customer.setPassword(passwordEncoder.encode(password1));
            customerService.save(customer);
            return "login";
        }
        else{
            return "/change_password/"+customer.getCode();
        }

    }
    @GetMapping("/user/{id}/other")
    public String user_friend(@PathVariable int id,Model model){
        Customer user =(Customer) customerServiceImpl.loadUserById(id);
        model.addAttribute("user",user);
        return "other_user";
    }

    @PostMapping("/upload_avatar")
    public String upload_avatar(@RequestParam MultipartFile file) throws IOException {

       String path =  System.getProperty("user.dir")+ File.separator
                +"src"+File.separator+
                "main"+File.separator+
                "resources"+File.separator+
                "static" +File.separator+
                "avatars"+File.separator;

        File avatar = new File(path + file.getOriginalFilename());
        file.transferTo(avatar);
        Customer user = (Customer)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setImage(path+file.getOriginalFilename());
customerService.save(user);
        return "user";
    }


}
