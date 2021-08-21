package com.github.simplefocals.service;

import com.github.simplefocals.entity.User;
import com.github.simplefocals.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //the username is email
    public User getUserByUsername(String email){
        return userRepository.getUserByUsername(email);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
