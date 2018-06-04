package com.shop.domain.entity;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double price;
    //TODO connect to Entity
    @Column(name = "user_id")
    private int userId;

    //TODO connect to Entity
    @Column(name = "category_id")
    private int categoryId;

    private int status;


    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductDescription> productDescription;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<ProductDescription> getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(List<ProductDescription> productDescription) {
        this.productDescription = productDescription;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int userId) {
        this.userId = userId;
    }

    public int getCategory_id() {
        return categoryId;
    }

    public void setCategory_id(int category_id) {
        this.categoryId = category_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}