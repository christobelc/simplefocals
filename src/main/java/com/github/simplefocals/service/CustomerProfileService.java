package com.github.simplefocals.service;

import com.github.simplefocals.entity.CustomerProfile;
import com.github.simplefocals.repository.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomerProfileService {

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    public void addCustomerProfile(CustomerProfile customerProfile){
        customerProfileRepository.save(customerProfile);
    }

    public Optional<CustomerProfile> getCustomerProfile(Integer id){
        return customerProfileRepository.findById(id);
    }

}
