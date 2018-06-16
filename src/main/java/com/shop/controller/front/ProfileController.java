package com.shop.controller.front;

import com.shop.domain.entity.Product;
import com.shop.domain.entity.User;
import com.shop.repository.ProductRepository;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;
    @RequestMapping(value = "/profile")

    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("front/profile/index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (user == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
        modelAndView.addObject("products",productService.getUserProducts(user.getId()));


        return modelAndView;
    }
    @RequestMapping(value = "product/delete/{id}")
    public String delete(@PathVariable("id") int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (user == null){
           return "redirect:/login";
        }
        Product one =  productRepository.findOne(id);
        if (one.getUserId() == user.getId()){
            productRepository.delete(one.getId());
        }
        return "redirect:/profile";
    }
}
