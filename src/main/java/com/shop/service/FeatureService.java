package com.shop.service;

import com.shop.domain.dto.FeatureDto;
import com.shop.domain.dto.VariantDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeatureService {
    private JdbcTemplate jdbcTemplate;

    public FeatureService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<Integer,FeatureDto> getFeaturesByCategory(int category_id){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT pf.id as feature_id,pfd.name,fv.id,fvd.variant FROM `category_feature_rel` cfr " +
                "INNER JOIN product_features pf ON (pf.id = cfr.feature_id) INNER JOIN product_features_descriptions pfd " +
                "ON (pfd.feature_id = pf.id) INNER JOIN feature_variants fv ON (fv.feature_id = pf.id) " +
                "LEFT JOIN feature_variant_descriptions fvd ON (fvd.variant_id=fv.id) " +
                "WHERE pf.status=1 and fvd.lang_id=? AND pfd.lang_id=? AND cfr.category_id=?",1,1,category_id);

        Map<Integer,FeatureDto> featureDtoMap = new HashMap<>();
        Integer key;
        String featureName , variantName;
        int variantId;
        List<String> list = new ArrayList<>();

        for (Map<String, Object> map : maps) {
            key = (Integer) map .get("feature_id");
            featureName = (String) map.get("name");
            variantId = (int) map.get("id");
            variantName = (String) map.get("variant");
            FeatureDto featureDto = featureDtoMap.get(key);
            if (featureDto == null){
                featureDtoMap.put(key,new FeatureDto(key,featureName));
                featureDto = featureDtoMap.get(key);
            }
            featureDto.addVatiant(new VariantDto(variantId,variantName));
        }

        return featureDtoMap;
    }
}
