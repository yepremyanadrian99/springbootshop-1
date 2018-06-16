package com.shop.service;

import com.shop.domain.dto.ProductDto;
import com.shop.domain.dto.ProductVatiantDto;
import com.shop.domain.entity.*;
import com.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private static String UPLOAD_DIR = System.getProperty("user.dir")+File.separator+"images"+File.separator+"products"+File.separator;

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDescriptionRepository productDescriptionRepository;
    @Autowired
    private ProductVariantRelRepository productVariantRelRepository;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    LanguageRepository languageRepository;

    @Qualifier("userRepository")
    @Autowired
    UserRepository userRepository;
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
    public int createProduct(Product p, MultipartFile[] multipartFiles){
        Product product =p;
        try {
            
            List<ProductDescription> productDescriptions = product.getProductDescriptions();
            List<ProductVariantRel> productVariantRels = product.getProductVariantRels();
            //remove form data
            product.setProductDescriptions(null);
            product.setProductVariantRels(null);

            Product saved = productRepository.save(product);
            if (productDescriptions != null){

                for (ProductDescription productDescription : productDescriptions) {
                    productDescription.setProduct(saved);
                    productDescriptionRepository.save(productDescription);

                }
            }

            if (productVariantRels !=null){
                for (ProductVariantRel productVariantRel : productVariantRels) {
                    if (productVariantRel.getVariantId() == 0) continue;
                    productVariantRel.setProduct(saved);
                    productVariantRelRepository.save(productVariantRel);
                }
            }

            saveImage(multipartFiles,product);
        }catch (RuntimeException e){
            return -1;//System.out.println("e.getMessage() = " + e.getMessage());
        }
        return product.getId();
    }
    public boolean saveImage(MultipartFile[] multipartFile,Product product){
        List<Image> images = new ArrayList<>();
        String filename;
        try {

            for (MultipartFile file : multipartFile) {
                byte[] bytes = file.getBytes();
                if (bytes.length == 0) continue;
                filename = product.getId()+"-"+file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR+File.separator+filename);

                Files.write(path,bytes);
                imageRepository.save(new Image(filename,product));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
    public Product getById(int id){
        return productRepository.findOne(id);
    }

    public List<Product> getTopProducts(){
        return productRepository.findByTop(1);
    }

    /**
     *
     * @param product_id
     * @param language_id
     * @return List of ProductVatiantDto
     */
    public List<ProductVatiantDto> getProductFeatures(int product_id,int language_id){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT pr.id,vd.variant,pfd.name FROM `product_variant_rel` pr " +
                "INNER JOIN feature_variant_descriptions vd USING(variant_id) INNER JOIN product_features_descriptions pfd USING(feature_id) " +
                "WHERE vd.lang_id=? AND pfd.lang_id=? AND pr.product_id=?",language_id,language_id,product_id);
        List<ProductVatiantDto> list = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            list.add(new ProductVatiantDto(
                    (String) map.get("name"),
                    (String) map.get("variant")

            ));
        }
        return list;
    }

    public User getProductOwner(int id){
        return userRepository.findById(id);
    }

    public List<Product> getUserProducts(int id){
        return productRepository.findByUserId(id);
    }
}
