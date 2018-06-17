package com.shop.controller;
//
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.ErrorController;
//import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
//public class MyErrorController implements ErrorController {
//
//    @RequestMapping("/")
//    public void handleRequest() {
//        throw new RuntimeException("test exception");
//    }
//
//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request) {
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//        if (status != null) {
//            Integer statusCode = Integer.valueOf(status.toString());
//
//            if(statusCode == HttpStatus.NOT_FOUND.value()) {
//                return "error/error-404";
//            }
//            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                return "error/error-500";
//            }
//        }
//        return "error/error";
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
//}
////
////
//////package com.baeldung.errorhandling.controllers;
//////
//////        import org.springframework.stereotype.Controller;
//////        import org.springframework.web.bind.annotation.GetMapping;
//////        import org.springframework.web.bind.annotation.PostMapping;
//////
//////@Controller
//////public class IndexController {
//////
//////    @GetMapping(value = {"/", ""})
//////    public String index() {
//////        return "index";
//////    }
//////
//////    @GetMapping(value = {"/server_error"})
//////    public String triggerServerError() {
//////        "ser".charAt(30);
//////        return "index";
//////    }
//////
//////    @PostMapping(value = {"/general_error"})
//////    public String triggerGeneralError() {
//////        return "index";
//////    }
//////
//////}
