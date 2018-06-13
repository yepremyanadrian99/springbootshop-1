package com.shop.service;

import com.shop.domain.entity.CategoryFeatureRel;
import com.shop.repository.CategoryFeatureRelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryFeatureRelService")
public class CategoryFeatureRelService {
    @Autowired
    private CategoryFeatureRelRepository categoryFeatureRelRepository;

    public CategoryFeatureRel addCategoryFeatureRel(int categoryId, int featureId) {
        CategoryFeatureRel categoryFeatureRel = new CategoryFeatureRel();
        categoryFeatureRel.setCategoryId(categoryId);
        categoryFeatureRel.setFeatureId(featureId);
        return categoryFeatureRelRepository.save(categoryFeatureRel);
    }

    public void deleteCategoryFeatureRel(int categoryFeatureRelId) {
        categoryFeatureRelRepository.delete(categoryFeatureRelId);
    }

    public List<CategoryFeatureRel> getAllCategoryFeatureRels() {
        List<CategoryFeatureRel> categoryFeatureRels = new ArrayList<>();
        Iterable<CategoryFeatureRel> all = categoryFeatureRelRepository.findAll();
        all.forEach(categoryFeatureRels::add);
        return categoryFeatureRels;
    }
}
