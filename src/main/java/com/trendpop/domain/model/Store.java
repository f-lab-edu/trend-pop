package com.trendpop.domain.model;

import java.time.LocalDate;

public record Store(String storeId, String storeName, String description, LocalDate startAt, LocalDate endAt,
                    String storeTypeId, String storeTypeName, String locationId, String locationName,
                    String imageUrl) {
}
