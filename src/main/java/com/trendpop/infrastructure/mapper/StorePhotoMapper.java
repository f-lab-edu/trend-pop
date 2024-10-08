package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.StorePhoto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StorePhotoMapper {
    @Select("SELECT * FROM store_photos WHERE store_id = #{storeId} ORDER BY `order` LIMIT 1")
    StorePhoto findMinOrderByStoreId(String storeId);

    @Select("SELECT id, store_id, image_url, `order` FROM store_photos WHERE store_id = #{storeId} AND `order` = #{minOrder}")
    StorePhoto findImageUrlByStoreIdAndOrder(String storeId, int minOrder);

    @Select("SELECT * FROM store_photos WHERE store_id = #{storeId}")
    List<StorePhoto> findStorePhotosByStoreId(String storeId);
}
