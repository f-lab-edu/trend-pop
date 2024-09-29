package com.trendpop.presentation.dto.response;

import com.trendpop.domain.model.*;

import java.time.LocalDate;

public record StoreResponse(String storeId, String storeName, String description, LocalDate startAt, LocalDate endAt,
                                   String storeType, String locationId, String locationName, String locationDetail,
                                   String imageUrl) {

    public static StoreResponse from(Store store, Location location, String imageUrl) {
        return new StoreResponse(
                store.id(),
                store.name(),
                store.description(),
                store.startAt(),
                store.endAt(),
                store.type(),
                location != null ? location.id() : null,
                location != null ? location.name() : null,
                store.locationDetail(),
                imageUrl
        );
    }
}
