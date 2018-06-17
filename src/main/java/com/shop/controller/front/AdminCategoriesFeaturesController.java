package com.shop.controller.front;

import com.shop.service.CategoryFeatureRelService;
import com.shop.service.CategoryService;
import com.shop.service.ProductFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

@Controller
public class AdminCategoriesFeaturesController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductFeatureService productFeatureService;
    @Autowired
    private CategoryFeatureRelService categoryFeatureRelService;

    @GetMapping("/admin/add/categories_features")
    public ModelAndView add(@RequestParam(required = false) String message) {
        ModelAndView modelAndView = new ModelAndView("/admin/add/categories_features");
        modelAndView.addObject("message", message);
        modelAndView.addObject("categories", categoryService.getAllCategories());
        modelAndView.addObject("features", productFeatureService.getAllFeatures());
        modelAndView.addObject("categoryFeatureRels", categoryFeatureRelService.getAllCategoryFeatureRels());
        return modelAndView;
    }

    @PostMapping("/admin/add/categories_features")
    public ModelAndView add(@RequestParam @NotNull int categoryId,
                            @RequestParam @NotNull int featureId) {
        categoryFeatureRelService.addCategoryFeatureRel(categoryId, featureId);
        return add(String.format("Feature: %d is now related with Category: %d!", featureId, categoryId));
    }

    @GetMapping("/admin/delete/categories_features")
    public ModelAndView delete(@RequestParam(required = false) String message) {
        ModelAndView modelAndView = new ModelAndView("/admin/delete/categories_features");
        modelAndView.addObject("message", message);
        modelAndView.addObject("categories", categoryService.getAllCategories());
        modelAndView.addObject("features", productFeatureService.getAllFeatures());
        modelAndView.addObject("categoryFeatureRels", categoryFeatureRelService.getAllCategoryFeatureRels());
        return modelAndView;
    }

    @PostMapping("/admin/delete/categories_features")
    public ModelAndView delete(@RequestParam @NotNull int categoryFeatureRelId) {
        categoryFeatureRelService.deleteCategoryFeatureRel(categoryFeatureRelId);
        return delete(String.format("Category Feature Rel %d successfully deleted!", categoryFeatureRelId));
    }
}
