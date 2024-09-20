package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.StorePhoto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StorePhotoMapper {
    @Select("<script>" +
            "SELECT sp.id, sp.store_id, sp.image_url, sp.`order` " +
            "FROM store_photos sp " +
            "WHERE sp.store_id IN " +
            "<foreach item='storeId' collection='storeIds' open='(' separator=',' close=')'>" +
            "#{storeId}" +
            "</foreach> " +
            "AND sp.`order` = ( " +
            "    SELECT MIN(s.`order`) " +
            "    FROM store_photos s " +
            "    WHERE s.store_id = sp.store_id " +
            "    AND s.deleted = FALSE " +
            ")" +
            "</script>")
    List<StorePhoto> findMainStorePhotosByIds(@Param("storeIds") List<String> storeIds);
}
