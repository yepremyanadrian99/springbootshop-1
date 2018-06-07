package com.shop.controller.front;

import com.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Controller
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    EntityManager entityManager;
    @RequestMapping("/category/{category_id}/{page_num}" )
    public ModelAndView index(@PathVariable(value = "category_id") int category_id
            , @PathVariable(value = "page_num",required = false) int page_num){
        ModelAndView modelAndView = categoryService.loadCategory(category_id, page_num);
    //
        return modelAndView;
    }

    @RequestMapping("/category/{category_id}" )
    public ModelAndView index(@PathVariable(value = "category_id") int category_id){
        ModelAndView modelAndView = categoryService.loadCategory(category_id, 1);
        //
        return modelAndView;
    }
    @RequestMapping("/categorytest" )
    public String test(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = criteriaBuilder.createQuery();
        // TypedQuery<Product> select_p_from_product_p = entityManager.createQuery("select p from Product p", Product.class);
       // System.out.println("select_p_from_product_p = " + select_p_from_product_p);
        return "sdf";
    }
}