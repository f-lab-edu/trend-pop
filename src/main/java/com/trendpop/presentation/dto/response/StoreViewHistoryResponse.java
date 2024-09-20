package com.trendpop.presentation.dto.response;

import com.trendpop.domain.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StoreViewHistoryResponse(String storeId, String storeName, String description, LocalDate startAt, LocalDate endAt,
                                       String storeTypeId, String storeTypeName, String locationId, String locationName,
                                       String imageUrl, LocalDateTime viewedAt) {

    public static StoreViewHistoryResponse from(Store store, StoreResponse storeResponse, StoreViewHistory storeViewHistory) {
        return new StoreViewHistoryResponse(
                store.id(),
                store.name(),
                store.description(),
                store.startAt(),
                store.endAt(),
                storeResponse.storeTypeId(),
                storeResponse.storeTypeName(),
                storeResponse.locationId(),
                storeResponse.locationName(),
                storeResponse.imageUrl(),
                storeViewHistory.viewedAt()
        );
    }
}