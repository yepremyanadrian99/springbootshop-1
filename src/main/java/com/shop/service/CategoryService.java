package com.shop.service;

import com.shop.domain.dto.CategoryDto;
import com.shop.domain.dto.ProductDto;
import com.shop.domain.entity.Category;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    //TODO get from session
    private static final int ITEM_PER_PAGE = 5;

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    //ProductRepository productRepository;
    private UserRepository userRepository;

    public CategoryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        //this.categoryRepository = categoryRepository;
        //jdbcTemplate.
    }

    public Category addOrUpdateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.delete(categoryId);
    }

    public List<ProductDto> getProducts(int cid, int page, int itemPerPage) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT p.*,d.name,d.description " +
                "from products as p INNER JOIN product_descriptions as d " +
                "on (p.id = d.product_id) where d.lang_id=? and p.category_id=? LIMIT ?,?", 1, cid, (page - 1) * itemPerPage, itemPerPage);
        System.out.println(maps);

        List<ProductDto> productDtos = new ArrayList<>();

        for (Map<String, Object> map : maps) {
            System.out.println(map.get("id"));
            productDtos.add(new ProductDto((Integer) map.get("id")
                    , Double.toString((Double) map.get("price"))
                    , (String) map.get("name"))
            );
        }
        return productDtos;
    }

    @Cacheable(cacheNames = "categories")
    public List<CategoryDto> getCategoriesTree(int startingId) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        getCategory(startingId, categoryDtos);
        return categoryDtos;
    }

    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select c.id,d.name,d.description from categories c " +
                "inner join category_descriptions d" +
                " on (c.id = d.category_id)");
        for (Map<String, Object> map : maps) {
            categoryDtos.add(new CategoryDto((int) map.get("id"), (String) map.get("name")));
        }
        return categoryDtos;
    }

    public void getCategory(int parentId, List<CategoryDto> categoryDtos) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select c.id,d.name,d.description from categories c " +
                "inner join category_descriptions d" +
                " on (c.id = d.category_id) where c.parent_id=?", parentId);
        for (Map<String, Object> map : maps) {
            categoryDtos.add(new CategoryDto((int) map.get("id"), (String) map.get("name")));
        }

        for (CategoryDto categoryDto : categoryDtos) {
            categoryDto.setSubcategories(new ArrayList<CategoryDto>());

            getCategory(categoryDto.getId(), categoryDto.getSubcategories());
        }
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public ModelAndView loadCategory(int category_id, int page_num) {
        int pages_count = productRepository.countByCategoryId(category_id);
        ;

        pages_count = (pages_count % ITEM_PER_PAGE > 0) ? pages_count / ITEM_PER_PAGE + 1 : pages_count / ITEM_PER_PAGE;

        List<ProductDto> products = getProducts(category_id, page_num, ITEM_PER_PAGE);
        ModelAndView modelAndView = new ModelAndView("front/blocks/products/search");
        modelAndView.addObject("products", products);
        modelAndView.addObject("pages", pages_count);

        return modelAndView;
    }
}
