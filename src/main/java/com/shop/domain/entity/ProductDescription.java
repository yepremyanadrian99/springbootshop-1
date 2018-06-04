package com.shop.domain.entity;
import javax.persistence.*;
@Entity
@Table(name = "product_descriptions")
public class ProductDescription {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private String description;
    @Column(name = "lang_id")
    private int langId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public int getLang_id() {
        return langId;
    }

    public void setLang_id(int langId) {
        this.langId = langId;
    }

}
