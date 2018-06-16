package com.shop.controller.front;

import com.shop.domain.entity.ProductFeature;
import com.shop.domain.entity.ProductFeatureDescription;
import com.shop.service.ProductFeatureDescriptionService;
import com.shop.service.ProductFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

@Controller
public class AdminFeaturesController {
    @Autowired
    private ProductFeatureService featureService;
    @Autowired
    private ProductFeatureDescriptionService featureDescriptionService;

    @GetMapping("/admin/add/features")
    public ModelAndView add(@RequestParam(required = false, name = "message") String message) {
        ModelAndView modelAndView = new ModelAndView("/admin/add/features");
        modelAndView.addObject("message", message);
        modelAndView.addObject("features", featureService.getAllFeatures());
        return modelAndView;
    }

    @PostMapping("/admin/add/features")
    public ModelAndView add(@RequestParam @NotNull String name1,
                            @RequestParam @NotNull String name2,
                            @RequestParam @NotNull String name3,
                            @RequestParam(defaultValue = "") String description1,
                            @RequestParam(defaultValue = "") String description2,
                            @RequestParam(defaultValue = "") String description3,
                            @RequestParam(defaultValue = "1") int status) {
        ProductFeature feature = new ProductFeature();
        feature.setStatus(status);
        feature = featureService.addProductFeature(feature);

        ProductFeatureDescription featureDescription;
        String[] names = {name1, name2, name3};
        String[] descriptions = {description1, description2, description3};

        for (int i = 0; i < names.length; ++i) {
            featureDescription = new ProductFeatureDescription();
            featureDescription.setProductFeature(feature);
            featureDescription.setName(names[i]);
            featureDescription.setDescription(descriptions[i]);
            featureDescription.setLangId(i + 1);
            featureDescriptionService.addFeatureDescription(featureDescription);
        }

        return add(String.format("Feature %s successfully saved!", name2));
    }

    @GetMapping("/admin/delete/features")
    public ModelAndView delete(@RequestParam(required = false, name = "message") String message) {
        ModelAndView modelAndView = new ModelAndView("/admin/delete/features");
        modelAndView.addObject("message", message);
        modelAndView.addObject("features", featureService.getAllFeatures());
        return modelAndView;
    }

    @PostMapping("/admin/delete/features")
    public ModelAndView delete(@RequestParam @NotNull int featureId) {
        featureDescriptionService.deleteFeatureDescriptionByFeatureId(featureId);
        featureService.deleteByFeatureId(featureId);
        return delete(String.format("Feature %d successfully deleted!", featureId));
    }
}
