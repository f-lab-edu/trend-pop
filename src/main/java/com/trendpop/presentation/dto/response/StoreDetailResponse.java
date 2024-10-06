package com.trendpop.presentation.dto.response;

import com.trendpop.domain.model.Store;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record StoreDetailResponse(String id, String name, String description, String type, String locationId,
                                  LocalDate startAt, LocalDate endAt, LocalTime openTime, LocalTime closeTime,
                                  int price, boolean reserveClosed, String locationDetail) {

    public static StoreDetailResponse from(Store store) {
        return new StoreDetailResponse(
                store.id(),
                store.name(),
                store.description(),
                store.type(),
                store.locationId(),
                store.startAt(),
                store.endAt(),
                store.openTime(),
                store.closeTime(),
                store.price(),
                store.reserveClosed(),
                store.locationDetail()
        );
    }
}
