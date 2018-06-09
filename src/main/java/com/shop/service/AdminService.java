package com.shop.service;

import com.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminService {
    @Autowired
    private CategoryRepository categoryRepository;

    
}
