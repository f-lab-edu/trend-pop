package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {
    @Select("SELECT store_id FROM recommended_stores WHERE is_active = TRUE ORDER BY priority")
    List<String> findActiveRecommendedStoreIdsOrderByPriority();

    @Select("SELECT * FROM stores WHERE id = #{storeId}")
    Store findNonDeletedStoreById(String storeId);
}
