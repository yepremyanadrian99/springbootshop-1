package com.shop.service;

import com.shop.domain.dto.ProductDto;
import com.shop.domain.entity.Product;
import com.shop.domain.entity.ProductDescription;
import com.shop.domain.entity.ProductVariantRel;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductRepository productRepository;
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
    public boolean createProduct(MultiValueMap<String, String> formData){
//        Product product = new Product();
//        product.setCategoryId(Integer.parseInt(String.valueOf(formData.get("categoryId"))));
//        product.setPrice(Double.parseDouble(String.valueOf(formData.get("price"))));
//        product.setStatus(0);
//        ProductDescription productDescription = new ProductDescription();
//        productDescription.setName(String.valueOf(formData.get("name")));
//        productDescription.setDescription(String.valueOf(formData.get("description")));
//        productDescription.setLangId(Integer.parseInt(String.valueOf(formData.get("lang_id"))));
//
//        product.addDescription(productDescription);
//        product.addVariant( new ProductVariantRel(1,2));
//        productRepository.save(product);
//        System.out.println(1);
        return true;
    }
}
