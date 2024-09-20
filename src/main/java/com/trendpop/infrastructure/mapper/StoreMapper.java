package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface StoreMapper {
    @Select("SELECT store_id FROM recommended_stores WHERE is_active = TRUE ORDER BY priority")
    List<String> findRecommendedStoreIds();

    @Select("<script>" +
            "SELECT * FROM stores WHERE id IN " +
            "<foreach item='storeId' collection='storeIds' open='(' separator=',' close=')'>" +
            "#{storeId}" +
            "</foreach> " +
            "AND deleted = FALSE" +
            "</script>")
    List<Store> findStoresByIds(@Param("storeIds") List<String> storeIds);
}
