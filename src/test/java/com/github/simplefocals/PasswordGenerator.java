package com.github.simplefocals;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //The admin password
        String rawData = "12345678";
        String encodedPassword = bCryptPasswordEncoder.encode(rawData);
        System.out.println("admin password: " + encodedPassword);

        //The test customer password
        rawData = "87654321";
        encodedPassword = bCryptPasswordEncoder.encode(rawData);
        System.out.println("customer password: " + encodedPassword);
    }


}
