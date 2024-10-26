package com.trendpop.domain.model;

import lombok.With;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(long id, String userId, String storeId, LocalDate visitAt, LocalTime visitTime,
                          int guestCount, @With ReservationStatus status) {
}
