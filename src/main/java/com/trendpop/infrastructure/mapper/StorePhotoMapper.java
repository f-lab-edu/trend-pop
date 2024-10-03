package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.StorePhoto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StorePhotoMapper {
    @Select("SELECT MIN(`order`) FROM store_photos WHERE store_id = #{storeId} AND deleted = FALSE")
    Integer findMinOrderByStoreId(String storeId);

    @Select("SELECT id, store_id, image_url, `order` FROM store_photos WHERE store_id = #{storeId} AND `order` = #{minOrder}")
    StorePhoto findImageUrlByStoreIdAndOrder(String storeId, int minOrder);
}
