package com.trendpop.presentation.dto.response;

import com.trendpop.domain.model.*;

import java.time.LocalDate;

public record ReservationCountResponse(String storeId, String storeName, String description, LocalDate startAt, LocalDate endAt,
                                       String storeTypeId, String storeTypeName, String locationId, String locationName,
                                       String imageUrl, int reservationCount) {

    public static ReservationCountResponse from(Store store, StoreResponse storeResponse, StoreReservationCount storeReservationCount) {
        return new ReservationCountResponse(
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
                storeReservationCount.reservationCount()
        );
    }
}
