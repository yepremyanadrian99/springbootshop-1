package com.shop.controller.front;

import com.shop.domain.dto.CategoryDto;
import com.shop.domain.entity.Language;
import com.shop.service.CategoryService;
import com.shop.service.LanguageService;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    private Language language;
    @Autowired
    ProductService productService;
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView view(){

        List<CategoryDto> categoriesTree = categoryService.getCategoriesTree(0,language.getId());

        ModelAndView modelAndView = new ModelAndView("front/home/index");
        modelAndView.addObject("categoriesTree",categoriesTree);

        modelAndView.addObject("products",productService.getTopProducts());
        modelAndView.addObject("subcategories",categoryService.getSubcategories(0,language.getId()));

        return  modelAndView;
    }
}
