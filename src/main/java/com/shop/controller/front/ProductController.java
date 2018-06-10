package com.shop.controller.front;

import com.shop.domain.dto.CategoryDto;
import com.shop.domain.dto.FeatureDto;
import com.shop.domain.dto.ProductDto;
import com.shop.domain.dto.ProductVatiantDto;
import com.shop.domain.entity.*;
import com.shop.repository.ProductDescriptionRepository;
import com.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImageService imageService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductFeatureService featureService;
    @Autowired
    private ProductDescriptionRepository productDescriptionRepository;
    @Autowired
    LanguageService languageService;
    @Autowired
    Language language;

    @Autowired
    UserService userService;
    @RequestMapping(value = "/product/add",method = RequestMethod.GET)
    public ModelAndView add(){


        langInit();
        List<CategoryDto> categoriesTree = categoryService.getCategoriesTree(0,language.getId());

        ModelAndView modelAndView = new ModelAndView("front/blocks/products/add");
        modelAndView.addObject("categories",categoriesTree);
        modelAndView.addObject("language",language);
        modelAndView.addObject("languages",languageService.getAll());
        return modelAndView;
    }

    @RequestMapping(value = "/product/create",method = RequestMethod.POST)
    public String create(@ModelAttribute Product product, @RequestParam(name = "files")MultipartFile[] multipartFiles){
        //TODO add images, and validation
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (user != null){
            product.setUserId(user.getId());
            int pid = productService.createProduct(product,multipartFiles);
            if (pid < 0)
                return "redirect:/product/add/";
            return "redirect:view/"+pid;
        }
        return "redirect:/login";
    }
    @RequestMapping(value = "/product/view/{id}")
    public ModelAndView preview(@PathVariable("id") int id){

        langInit();

        ModelAndView modelAndView = new ModelAndView("front/blocks/products/preview");
        Product product = productService.getById(id);
        List<ProductVatiantDto> productFeatures = productService.getProductFeatures(id, language.getId());
        User productOwner = productService.getProductOwner(product.getUserId());
        modelAndView.addObject("product",product);
        modelAndView.addObject("language",language);
        modelAndView.addObject("productFeatures",productFeatures);
        modelAndView.addObject("languages",languageService.getAll());
        modelAndView.addObject("productOwner",productOwner);
        modelAndView.addObject("subcategories",categoryService.getSubcategories(0,language.getId()));
        return modelAndView;
    }
    @RequestMapping(value = "/product/add/{category_id}")
    public ModelAndView form(@PathVariable("category_id")int category_id){
        langInit();
        Map<Integer, FeatureDto> featuresByCategory = featureService.getFeaturesByCategory(category_id,language.getId());
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
        modelAndView.addObject("language",language);
        modelAndView.addObject("languages",languageService.getAll());

        return modelAndView;
    }

    @RequestMapping(value = "/product/search")
    public ModelAndView search(@RequestParam(value = "q",required = false,defaultValue ="") String keyword){
        langInit();
        List<ProductDto> list = productService.searchProducts(keyword, 1, 12);
        ModelAndView modelAndView = new ModelAndView("front/blocks/products/search");
        modelAndView.addObject("products",list);
        modelAndView.addObject("language",language);
        modelAndView.addObject("languages",languageService.getAll());
        return modelAndView;
    }

    @RequestMapping(value = "/user/{id}/products")
    public ModelAndView getUserProducts(@PathVariable("id") int id){

        ModelAndView modelAndView = new ModelAndView("front/blocks/products/userProducts");
        modelAndView.addObject("owner",productService.getProductOwner(id));
        modelAndView.addObject("products",productService.getUserProducts(id));
        modelAndView.addObject("language",language);
        modelAndView.addObject("languages",languageService.getAll());
        return modelAndView;
    }
    public void langInit(){
        if (language.getId() == 0){
            languageService.initCurrentLang(language,1);
        }
    }
}
