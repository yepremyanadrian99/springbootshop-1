package com.shop.controller.front;

import com.shop.domain.dto.CategoryDto;
import com.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView view(){
        //userService.getUserById(0);
        Number countOf = categoryService.getCountOf(1);

        List<CategoryDto> categoriesTree = categoryService.getCategoriesTree(0);

        ModelAndView modelAndView = new ModelAndView("front/home/index");
        modelAndView.addObject("categoriesTree",categoriesTree);
        return  modelAndView;
    }
}
