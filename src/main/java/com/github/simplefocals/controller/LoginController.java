package com.github.simplefocals.controller;


import com.github.simplefocals.exception.UserExistsException;
import com.github.simplefocals.entity.Role;
import com.github.simplefocals.entity.User;
import com.github.simplefocals.service.RoleService;
import com.github.simplefocals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //login page
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //register page
    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    //error in register page
    @GetMapping("/registerError")
    public String getRegisterError(){
        return "registerError";
    }

    // post details for registering a new customer
    @PostMapping("/register")
    public String postRegister(@ModelAttribute("user") User user) throws UserExistsException {
        //if the User is not registered on the system
        if(userService.getUserByUsername(user.getEmail()) == null){
            // assign the user's password to String
            String rawData = user.getPassword();
            //set the users password to the encoded value
            user.setPassword(bCryptPasswordEncoder.encode(rawData));

            //make a Roles List object
            List<Role> roles = new ArrayList<>();
            //add this customer to the roles
            roles.add(roleService.getCustomerRole());
            //set the users role to customer
            user.setRoles(roles);
            //write the user to persistent storage
            userService.saveUser(user);
            //redirect to login
            return "redirect:/login";
        }else{
            return "registerError";
        }
    }
}
