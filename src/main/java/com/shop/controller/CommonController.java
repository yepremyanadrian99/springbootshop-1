package com.shop.controller;

import com.shop.domain.entity.Language;
import com.shop.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CommonController {

//    @ExceptionHandler({RuntimeException.class})
//    public ModelAndView handleException(){
//        ModelAndView modelAndView = new ModelAndView("error/error");
//        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
//        return modelAndView;
//    }
//    @RequestMapping(value = "/error")
//    public ModelAndView errorPage(){
//        ModelAndView modelAndView = new ModelAndView("error/error");
//        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
//        return modelAndView;
//    }
    @Autowired
    LanguageService languageService;
    @Autowired
    Language language;

    @ModelAttribute
    public void addCommonData(Model model){
        model.addAttribute("languages",languageService.getAll());
        if (language.getId() == 0){
            languageService.initCurrentLang(language,1);
        }
        model.addAttribute("language",language);


    }
}
