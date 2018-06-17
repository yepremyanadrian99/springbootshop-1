package com.shop.controller.front;

import com.shop.domain.dto.EditPassword;
import com.shop.domain.dto.EditUser;
import com.shop.domain.entity.Product;
import com.shop.domain.entity.User;
import com.shop.repository.ProductRepository;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ProfileController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @RequestMapping(value = "/userinfo/password", method = RequestMethod.GET)
    public ModelAndView showPassword() {
        ModelAndView modelAndView = new ModelAndView("front/userinfo/updatePassword");
        EditPassword editPassword = new EditPassword();
        modelAndView.addObject("editPassword",editPassword);
        return modelAndView;
    }//Arsen

    @RequestMapping(value = "/userinfo/password", method = RequestMethod.POST)
    public ModelAndView updatePassword(@Valid EditPassword editPassword, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("front/userinfo/updatePassword");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("front/userinfo/updatePassword");
        }else if( !(bCryptPasswordEncoder.matches(editPassword.getOldPassword(),user.getPassword())) ){
            bindingResult.rejectValue("oldPassword", "error.editPassword", "There is not valid password");
        }else if( !((editPassword.getNewPassword()).equals(editPassword.getRepitPassword())) ){
            bindingResult.rejectValue("repitPassword", "error.editPassword", "NewPassword not equals repitPassword");
        }else {
            user.setPassword(editPassword.getNewPassword());
            userService.updatePassword(user);
            modelAndView.addObject("successMessage", "Password is edit successfully");
            modelAndView.addObject("editPassword", new EditPassword());
            modelAndView.setViewName("front/userinfo/updatePassword");
        }
        return modelAndView;
    } //Arsen

    @RequestMapping(value = "/userinfo/user", method = RequestMethod.GET)
    public ModelAndView showUser() {
        ModelAndView modelAndView = new ModelAndView("front/userinfo/updateUser");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("user", user);
        return modelAndView;
    }//Arsen

    @RequestMapping(value = "/userinfo/user", method = RequestMethod.POST)
    public ModelAndView showUser(@Valid EditUser editUser, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("front/userinfo/updateUser");
        }else{
            user.setName(editUser.getName());
            user.setLastName(editUser.getLastName());
            userService.updateUser(user);
            modelAndView.addObject("successMessage", "User is edit successfully");
            modelAndView.addObject("user", new EditUser());
            modelAndView.setViewName("front/userinfo/updateUser");
        }
        return modelAndView;
    }
}
