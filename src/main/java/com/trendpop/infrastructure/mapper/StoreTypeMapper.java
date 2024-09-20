package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.StoreType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreTypeMapper {
    @Select("<script>" +
            "SELECT * FROM store_types WHERE id IN " +
            "<foreach item='storeTypeId' collection='storeTypeIds' open='(' separator=',' close=')'>" +
            "#{storeTypeId}" +
            "</foreach>" +
            "</script>")
    List<StoreType> findStoreTypesByIds(@Param("storeTypeIds") List<String> storeTypeIds);
}
