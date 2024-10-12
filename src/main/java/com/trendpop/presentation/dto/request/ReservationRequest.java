package com.trendpop.presentation.dto.request;

import com.trendpop.domain.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    private long id;
    private String userId;
    private String storeId;
    private LocalDate visitAt;
    private LocalTime visitTime;
    private int guestCount;
    private String status;

    public Reservation toDomain() {
        return new Reservation(id, userId, storeId, visitAt, visitTime, guestCount, status);
    }
}
