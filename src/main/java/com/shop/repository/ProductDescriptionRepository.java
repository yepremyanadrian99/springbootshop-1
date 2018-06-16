package com.shop.repository;

import com.shop.domain.entity.ProductDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductDescriptionRepository extends CrudRepository<ProductDescription,Long> {
//    @Query("select d from ProductDescription ")
//    int findByDescription();
}
