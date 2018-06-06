package com.shop.controller.front;

import com.shop.domain.dto.ProductDto;
import com.shop.domain.entity.Product;
import com.shop.repository.ProductRepository;
import com.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class CategoryController {
    //TODO get from session
    private static final int ITEM_PER_PAGE = 5;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    ProductRepository productRepository;
    @RequestMapping("/category/{category_id}/{page_num}" )
    public ModelAndView index(@PathVariable(value = "category_id") int category_id
            , @PathVariable(value = "page_num") int page_num){

        int pages_count = productRepository.countByCategoryId(category_id);;

        pages_count = (pages_count%ITEM_PER_PAGE >0) ? pages_count/ITEM_PER_PAGE + 1 : pages_count/ITEM_PER_PAGE;

        List<ProductDto> products = categoryService.getProducts(category_id, page_num,ITEM_PER_PAGE);
        ModelAndView modelAndView = new ModelAndView("categories/index");
        modelAndView.addObject("products",products);
        modelAndView.addObject("pages",pages_count);

        return modelAndView;
    }
}