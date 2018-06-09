package com.shop.service;

import com.shop.domain.entity.CategoryDescription;
import com.shop.repository.CategoryDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("categoryDescription")
public class CategoryDescriptionService {
    @Autowired
    private CategoryDescriptionRepository categoryDescriptionRepository;

    public void addCategoryDescription(CategoryDescription categoryDescription) {
        categoryDescriptionRepository.save(categoryDescription);
    }
}
