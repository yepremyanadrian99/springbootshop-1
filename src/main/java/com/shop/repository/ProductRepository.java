package com.shop.repository;

import com.shop.domain.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> {
//    @Query("select p from products p inner join product_descriptions d on (p.id = d.product_id)")
//    List<Product> searchProduct();
}
