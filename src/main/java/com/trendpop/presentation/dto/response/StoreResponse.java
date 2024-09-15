package com.trendpop.presentation.dto.response;

import com.trendpop.domain.model.Store;

import java.time.LocalDate;

public record StoreResponse(String storeId, String storeName, String description, LocalDate startAt, LocalDate endAt,
                                   String storeTypeId, String storeTypeName, String locationId, String locationName,
                                   String imageUrl) {
    public static StoreResponse from(Store store) {
        return new StoreResponse(
                store.storeId(), store.storeName(), store.description(), store.startAt(), store.endAt(), store.storeTypeId(), store.storeTypeName(), store.locationId(),
                store.locationName(), store.imageUrl()
        );
    }
}
