package com.shop.controller.front;

import com.shop.domain.dto.CategoryDto;
import com.shop.domain.dto.FeatureDto;
import com.shop.domain.dto.ProductDto;
import com.shop.domain.entity.Product;
import com.shop.domain.entity.ProductDescription;
import com.shop.domain.entity.ProductVariantRel;
import com.shop.repository.ProductDescriptionRepository;
import com.shop.repository.ProductRepository;
import com.shop.service.CategoryService;
import com.shop.service.ProductFeatureService;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    ProductFeatureService productFeatureService;
    @Autowired
    private ProductDescriptionRepository productDescriptionRepository;

    @RequestMapping(value = "/product/add",method = RequestMethod.GET)
    public ModelAndView add(){

        List<CategoryDto> categoriesTree = categoryService.getCategoriesTree(0);

        ModelAndView modelAndView = new ModelAndView("front/blocks/products/add");
        modelAndView.addObject("categories",categoriesTree);

        return modelAndView;
    }

    @RequestMapping(value = "/product/create",method = RequestMethod.POST)
    public String create(@ModelAttribute Product product){
        //TODO add images, and validation
        boolean isCreated = productService.createProduct(product);
        System.out.println("product = " + product);
        //create product
        return "back";
    }
    @RequestMapping(value = "/product/add/{category_id}")
    public ModelAndView form(@PathVariable("category_id")int category_id){

        Map<Integer, FeatureDto> featuresByCategory = productFeatureService.getFeaturesByCategory(category_id);
        System.out.println(featuresByCategory);
        //TODO
        //get features connected to this category
        //SELECT * FROM `category_feature_rel` WHERE category_id=1 INNER JOIN product_features USING(feature_id)  ev ajln
        ModelAndView modelAndView = new ModelAndView("front/blocks/products/form");
        modelAndView.addObject("featureVariants",featuresByCategory);
        Product product = new Product();
        product.addDescription(new ProductDescription());
        product.addVariant(new ProductVariantRel());
        modelAndView.addObject("product",product);
        return modelAndView;
    }

    @RequestMapping(value = "/product/search")
    public ModelAndView search(@RequestParam(value = "q",required = false,defaultValue ="") String keyword){
        List<ProductDto> list = productService.searchProducts(keyword, 1, 12);
        ModelAndView modelAndView = new ModelAndView("front/blocks/products/search");
        modelAndView.addObject("products",list);
        return modelAndView;
    }
}
