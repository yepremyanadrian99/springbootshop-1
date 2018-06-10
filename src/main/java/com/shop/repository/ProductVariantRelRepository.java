package com.shop.repository;

import com.shop.domain.entity.ProductVariantRel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductVariantRelRepository extends CrudRepository<ProductVariantRel,Integer> {
}
