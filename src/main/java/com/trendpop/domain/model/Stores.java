package com.trendpop.domain.model;

import java.time.LocalDate;

public record Stores(String storeId, String storeName, String description, LocalDate startAt, LocalDate endAt,
                     String storeTypeId, String storeTypeName, String locationId, String locationName,
                     String imageUrl) { // TODO(@jiyoonkong): Stores 삭제 예정. Due Date: 2024.09.29)
}
