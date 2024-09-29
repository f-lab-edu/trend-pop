package com.trendpop.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Store(String id, String name, String description, String type, String locationId, LocalDate startAt, LocalDate endAt
                    , LocalTime openTime, LocalTime closeTime, int price, boolean reserveClosed, String locationDetail) {
}
