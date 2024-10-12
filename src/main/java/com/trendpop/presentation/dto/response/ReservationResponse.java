package com.trendpop.presentation.dto.response;

import com.trendpop.domain.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(long id, String userId, String storeId, LocalDate visitAt, LocalTime visitTime,
                                 int guestCount, String status) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.id(), reservation.userId(), reservation.storeId(), reservation.visitAt(),
                                       reservation.visitTime(), reservation.guestCount(), reservation.status());
    }
}
