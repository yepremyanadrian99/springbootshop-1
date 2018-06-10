package com.shop.service;

import com.shop.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userRoleService")
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Transactional
    public void deleteUserRoleByUserId(int userId) {
        userRoleRepository.deleteUserRoleByUserId(userId);
    }
}
