package com.github.simplefocals;


import com.stripe.Stripe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class SimplefocalsApplication {

    //this is the private key for my Stripe account.
    @PostConstruct
    public void setup(){
        Stripe.apiKey="sk_test_51JHr20IkXPmyXB6SMX8ekXNvL2Zf4QOJGWP16Kjbwsx" +
                "7XVIAnbpH3rhr1EdCZkLmuVTs1tsU30KgdNDG4IhZQ0fz0053QCUEwC";
    }
    public static void main(String[] args) {
        SpringApplication.run(SimplefocalsApplication.class, args);
    }
}
