package com.github.simplefocals.service;

import com.github.simplefocals.entity.Role;
import com.github.simplefocals.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getCustomerRole(){
        return roleRepository.findById(2).get();
    }
}
