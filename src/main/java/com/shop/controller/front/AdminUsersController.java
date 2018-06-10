package com.shop.controller.front;

import com.shop.service.UserRoleService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminUsersController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/admin/delete/users")
    public ModelAndView delete(@RequestParam(required = false) String message) {
        ModelAndView modelAndView = new ModelAndView("/admin/delete/users");
        modelAndView.addObject("message", message);
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }

    @PostMapping("/admin/delete/users")
    public ModelAndView delete(@RequestParam int userId) {
        userRoleService.deleteUserRoleByUserId(userId);
        userService.deleteUserById(userId);
        return delete(String.format("User %d successfully deleted!", userId));
    }
}
