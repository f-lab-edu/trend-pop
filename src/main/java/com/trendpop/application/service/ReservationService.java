package com.trendpop.application.service;

import com.trendpop.domain.model.Reservation;
import com.trendpop.infrastructure.mapper.LocationMapper;
import com.trendpop.infrastructure.mapper.ReservationMapper;
import com.trendpop.infrastructure.mapper.StoreMapper;
import com.trendpop.infrastructure.mapper.StorePhotoMapper;
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

    public List<StoreResponse> getMostPopularStores() {
        List<String> top10StoreIds = reservationMapper.findTop10StoreIdsOrderByReservationCounts();

        return top10StoreIds.stream()
                .map(storeService::createStoreResponse)
                .collect(Collectors.toList());
    }

    public ReservationResponse createReservation(Reservation reservation) {
        Reservation confirmedReservation = reservation.withStatus("CONFIRMED");
        reservationMapper.createReservation(confirmedReservation);
        return ReservationResponse.from(confirmedReservation);
    }

    public List<ReservationResponse> getReservationsByUserId(String userId) {
        List<Reservation> reservations = reservationMapper.findReservationsByUserId(userId);
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse updateReservation(Reservation reservation) {
        Reservation reservationInfo = reservationMapper.findReservationById(reservation.id());
        Reservation updatedReservationInfo = new Reservation(
                reservationInfo.id(),
                reservationInfo.userId(),
                reservationInfo.storeId(),
                Optional.ofNullable(reservation.visitAt()).orElse(reservationInfo.visitAt()),
                Optional.ofNullable(reservation.visitTime()).orElse(reservationInfo.visitTime()),
                reservation.guestCount() != 0 ? reservation.guestCount() : reservationInfo.guestCount(),
                reservationInfo.status()
        );
        reservationMapper.updateReservationInfo(updatedReservationInfo);
        Reservation updatedReservation = reservationMapper.findReservationById(reservation.id());
        return ReservationResponse.from(updatedReservation);
    }

    @Transactional
    public ReservationResponse updateReservationStatusVisited(Reservation reservation) {
        Reservation visitedReservation = reservation.withStatus("VISITED");
        reservationMapper.updateReservationStatus(visitedReservation);
        Reservation updatedReservation = reservationMapper.findReservationById(reservation.id());
        return ReservationResponse.from(updatedReservation);
    }

    @Transactional
    public ReservationResponse cancelReservation(Reservation reservation) {
        Reservation cancelLeddReservation = reservation.withStatus("CANCELLED");
        reservationMapper.updateReservationStatus(cancelLeddReservation);
        Reservation updatedReservation = reservationMapper.findReservationById(reservation.id());
        return ReservationResponse.from(updatedReservation);
    }
}