package com.trendpop.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RecommendedStore(String id, String storeId, int priority, String imageUrl, LocalDateTime recommendedAt) {
}
