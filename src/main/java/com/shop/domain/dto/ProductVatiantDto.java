package com.shop.domain.dto;

public class ProductVatiantDto {
    private String featureName;
    private String variantName;

    public ProductVatiantDto(String featureName, String variantName) {
        this.featureName = featureName;
        this.variantName = variantName;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }
}
