package com.shop.service;

import com.shop.domain.dto.ProductDto;
import com.shop.domain.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final JdbcTemplate jdbcTemplate;

    public ProductService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductDto> searchProducts(String keyword,int from, int itemPerPage){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT p.*,d.name,d.description " +
                "from products as p INNER JOIN product_descriptions as d " +
                "on (p.id = d.product_id) where d.lang_id=? and d.name like ? LIMIT ?,?", 1, "%"+keyword+"%", (from - 1) * itemPerPage, itemPerPage);
        //System.out.println(maps);

        List<ProductDto> productDtos = new ArrayList<>();

        for (Map<String, Object> map : maps) {
            System.out.println(map.get("id"));
            productDtos.add(new ProductDto((Integer)map.get("id")
                    ,(String) map.get("name")
                    ,Double.toString((Double)map.get("price")))
            );
        }
        return productDtos;
    }

}
