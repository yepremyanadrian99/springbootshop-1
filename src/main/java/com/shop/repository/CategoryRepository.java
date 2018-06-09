package com.shop.repository;

import com.shop.domain.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Category findById(int categoryId);
}
