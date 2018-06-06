package com.shop.domain.entity;


import javax.persistence.*;

@Entity
@Table(name = "product_features")
public class ProductFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int status;
}
