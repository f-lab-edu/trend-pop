package com.trendpop.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(long id, String userId, String storeId, LocalDate visitAt, LocalTime visitTime,
                          int guestCount, ReservationStatus status) {
    public Reservation withStatus(ReservationStatus newStatus) {
        return new Reservation(this.id, this.userId, this.storeId, this.visitAt, this.visitTime, this.guestCount, newStatus);
    }
}
