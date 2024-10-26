package com.trendpop.application.service;

import com.trendpop.domain.model.Reservation;
import com.trendpop.domain.model.ReservationStatus;
import com.trendpop.infrastructure.mapper.ReservationMapper;
import com.trendpop.presentation.dto.response.ReservationResponse;
import com.trendpop.presentation.dto.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final StoreService storeService;

    public List<StoreResponse> showMostPopularStores() {
        List<String> top10StoreIds = reservationMapper.findTop10StoreIdsOrderByReservationCounts();

        return top10StoreIds.stream()
                .map(storeService::createStoreResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse reserve(Reservation reservation) {
        Reservation confirmedReservation = reservation.withStatus(ReservationStatus.CONFIRMED);
        reservationMapper.create(confirmedReservation);
        return ReservationResponse.from(confirmedReservation);
    }

    public List<ReservationResponse> findReservationsByUserId(String userId) {
        List<Reservation> reservations = reservationMapper.findByUserId(userId);
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse updateReservation(Reservation reservation) {
        Reservation reservationRequest = reservationMapper.findById(reservation.id());
        Reservation updatedReservationRequest = new Reservation(
                reservationRequest.id(),
                reservationRequest.userId(),
                reservationRequest.storeId(),
                Optional.ofNullable(reservation.visitAt()).orElse(reservationRequest.visitAt()),
                Optional.ofNullable(reservation.visitTime()).orElse(reservationRequest.visitTime()),
                reservation.guestCount() != 0 ? reservation.guestCount() : reservationRequest.guestCount(),
                reservationRequest.status()
        );
        reservationMapper.update(updatedReservationRequest);
        Reservation updatedReservation = reservationMapper.findById(reservation.id());
        return ReservationResponse.from(updatedReservation);
    }

    @Transactional
    public ReservationResponse cancelReservation(Reservation reservation) {
        Reservation cancelLeddReservation = reservation.withStatus(ReservationStatus.CANCELLED);
        reservationMapper.updateStatus(cancelLeddReservation);
        Reservation updatedReservation = reservationMapper.findById(reservation.id());
        return ReservationResponse.from(updatedReservation);
    }
}
