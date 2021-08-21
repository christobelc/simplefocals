package com.github.simplefocals.service;

import com.github.simplefocals.entity.CustomUserDetails;
import com.github.simplefocals.entity.User;
import com.github.simplefocals.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //so we can retrieve the user from the db
    @Autowired
    private UserRepository userRepository;

    //method to get the user by email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.getUserByUsername(email);
        if(user == null){
            throw new UsernameNotFoundException("The email does not exist");
        }
        return new CustomUserDetails(user);
    }

    public String returnUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public String returnFirstName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        return user.getFirstName();
    }

    public String returnLastName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        return user.getLastName();
    }

    public Integer returnId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        return user.getId();
    }


}
