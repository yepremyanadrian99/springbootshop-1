package com.shop.service;

import com.shop.domain.dto.CategoryDto;
import com.shop.domain.dto.ProductDto;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    CategoryRepository categoryRepository;
    //@Autowired
    //ProductRepository productRepository;
    private UserRepository userRepository;
    public CategoryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        //this.categoryRepository = categoryRepository;
        //jdbcTemplate.
    }

    public List<ProductDto> getProducts(int cid, int page, int itemPerPage){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT p.*,d.name,d.description " +
                "from products as p INNER JOIN product_descriptions as d " +
                "on (p.id = d.product_id) where d.lang_id=? and p.category_id=? LIMIT ?,?", 1, cid, (page - 1) * itemPerPage, itemPerPage);
        System.out.println(maps);

        List<ProductDto> productDtos = new ArrayList<>();

        for (Map<String, Object> map : maps) {
            System.out.println(map.get("id"));
            productDtos.add(new ProductDto((Integer)map.get("id")
                    ,Double.toString((Double)map.get("price"))
                    ,(String) map.get("name"))
            );
        }
        return productDtos;
    }
    public Number getCountOf(int cid){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT count(*) as count FROM products p WHERE category_id=?", cid);
        return (Number) maps.get(0).get("count");
    }
    @Cacheable(cacheNames = "categories")
    public List<CategoryDto> getCategoriesTree(int startingId){

        List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();

         getCateory(startingId, categoryDtos);

        return categoryDtos;
    }

    public void getCateory(int parentId,List<CategoryDto> categoryDtos){

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select c.id,d.name,d.description from categories c inner join category_descriptions d" +
                " on (c.id = d.category_id) where c.parent_id=?", parentId);
        for (Map<String, Object> map : maps) {
            categoryDtos.add(new CategoryDto((int)map.get("id"),(String) map.get("name")));
        }

        for (CategoryDto categoryDto :categoryDtos){
            categoryDto.setSubcategories(new ArrayList<CategoryDto>());

            getCateory(categoryDto.getId(),categoryDto.getSubcategories());
        }
    }

}
