package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {
    @Select("SELECT * FROM stores WHERE id = #{storeId} AND deleted = FALSE")
    Store findStoreById(String storeId);
}
