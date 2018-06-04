package com.shop.repository;

import com.shop.domain.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Integer> {
//    @Query("SELECT count(p) as count FROM products p WHERE category_id=?1")
//    Long getCategoryProductCount(int category_id);
}
