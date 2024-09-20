package com.trendpop.domain.model;

import java.time.LocalDateTime;

public record StoreViewHistory(String storeId, LocalDateTime viewedAt) {
}
