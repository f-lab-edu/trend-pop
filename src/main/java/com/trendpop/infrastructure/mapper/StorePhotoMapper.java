package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.StorePhoto;
import com.trendpop.domain.model.StorePhotoMinOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StorePhotoMapper {
    @Select("<script>" +
            "SELECT store_id, MIN(`order`) AS min_order " +
            "FROM store_photos " +
            "WHERE deleted = FALSE " +
            "AND store_id IN " +
            "<foreach item='storeId' collection='storeIds' open='(' separator=',' close=')'>" +
            "#{storeId}" +
            "</foreach> " +
            "GROUP BY store_id" +
            "</script>")
    List<StorePhotoMinOrder> findMinOrderForStoreIds(@Param("storeIds") List<String> storeIds);

    @Select("<script>" +
            "SELECT sp.id, sp.store_id, sp.image_url, sp.`order` " +
            "FROM store_photos sp " +
            "WHERE (sp.store_id, sp.`order`) IN " +
            "<foreach item='minOrder' collection='minOrders' open='(' separator=',' close=')'>" +
            "(#{minOrder.storeId}, #{minOrder.minOrder})" +
            "</foreach> " +
            "AND sp.deleted = FALSE" +
            "</script>")
    List<StorePhoto> findStorePhotosByStoreIdAndOrder(@Param("minOrders") List<StorePhotoMinOrder> minOrders);
}
