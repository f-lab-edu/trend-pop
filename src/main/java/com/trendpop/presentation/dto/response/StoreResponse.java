package com.trendpop.presentation.dto.response;

import com.trendpop.domain.model.*;

import java.time.LocalDate;

public record StoreResponse(String storeId, String storeName, String description, LocalDate startAt, LocalDate endAt,
                                   String storeTypeId, String storeTypeName, String locationId, String locationName,
                                   String imageUrl) {
    public static StoreResponse from(Stores store) {
        return new StoreResponse(
                store.storeId(), store.storeName(), store.description(), store.startAt(), store.endAt(), store.storeTypeId(), store.storeTypeName(), store.locationId(),
                store.locationName(), store.imageUrl()
        );
    }

    public static StoreResponse from(Store store, StoreType storeType, Location location, StorePhoto storePhoto) {
        return new StoreResponse(
                store.id(),
                store.name(),
                store.description(),
                store.startAt(),
                store.endAt(),
                storeType != null ? storeType.id() : null,
                storeType != null ? storeType.name() : null,
                location != null ? location.id() : null,
                location != null ? location.name() : null,
                storePhoto != null ? storePhoto.imageUrl() : null
        );
    }
}
