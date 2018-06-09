package com.shop.controller.front;

import com.shop.domain.entity.Category;
import com.shop.domain.entity.CategoryDescription;
import com.shop.service.CategoryDescriptionService;
import com.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

@Controller
public class AdminController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryDescriptionService categoryDescriptionService;

    @GetMapping("admin/categories/add")
    public ModelAndView categoriesAdd() {
        ModelAndView modelAndView = new ModelAndView("admin/categories/add");
        modelAndView.addObject("categories", categoryService.getAllCategories());
        return modelAndView;
    }

    @PostMapping("/admin/categories/add")
    public String categoriesAdd(@RequestParam @NotNull int parentId,
                                @RequestParam(defaultValue = "0") int status,
                                @RequestParam @NotNull String name,
                                @RequestParam String description) {
        Category category = new Category();
        category.setParentId(parentId);
        category.setStatus(status);
        category.setLevel(categoryService.getCategoryLevel(parentId) + 1);
        categoryService.addCategory(category);
        CategoryDescription categoryDescription = new CategoryDescription();
        categoryDescription.setCategory(category);
        categoryDescription.setDescription(description);
        categoryDescription.setName(name);
        categoryDescriptionService.addCategoryDescription(categoryDescription);
        return "Category saved!";
    }
}
