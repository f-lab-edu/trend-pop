package com.trendpop.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StorePhotoMapper {
    @Select("SELECT MIN(`order`) FROM store_photos WHERE store_id = #{storeId} AND deleted = FALSE")
    Integer findMinOrderByStoreIdNonDeleted(String storeId);

    @Select("SELECT image_url FROM store_photos WHERE store_id = #{storeId} AND `order` = #{minOrder}")
    String findImageUrlByStoreIdAndOrder(String storeId, int minOrder);
}
