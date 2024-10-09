package com.trendpop.presentation.dto.response;

import com.trendpop.domain.model.StorePhoto;

public record StorePhotoResponse(String id, String storeId, String imageUrl, int order) {
    public static StorePhotoResponse from(StorePhoto storePhoto) {
        return new StorePhotoResponse(
                storePhoto.id(),
                storePhoto.storeId(),
                storePhoto.imageUrl(),
                storePhoto.order()
        );
    }
}
