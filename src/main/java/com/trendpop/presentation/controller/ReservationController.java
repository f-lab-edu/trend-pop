package com.trendpop.presentation.controller;

import com.trendpop.application.service.ReservationService;
import com.trendpop.presentation.dto.request.ReservationRequest;
import com.trendpop.presentation.dto.response.ReservationResponse;
import com.trendpop.presentation.dto.response.StoreResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/most-popular-stores")
    public List<StoreResponse> showMostPopularStores() {
        return reservationService.showMostPopularStores();
    }

    @PostMapping
    public ReservationResponse reserve(@RequestBody ReservationRequest request) {
        return reservationService.reserve(request.toDomain());
    }

    @GetMapping("/{userId}")
    public List<ReservationResponse> findReservationsByUserId(@PathVariable String userId) {
        return reservationService.findReservationsByUserId(userId);
    }

    @PatchMapping
    public ReservationResponse updateReservation(@RequestBody ReservationRequest request) {
        return reservationService.updateReservation(request.toDomain());
    }

    @PatchMapping("/update-status-visited")
    public ReservationResponse updateReservationStatusVisited(@RequestBody ReservationRequest request) {
        return reservationService.updateReservationStatusVisited(request.toDomain());
    }

    @PatchMapping("/cancel")
    public ReservationResponse cancelReservation(@RequestBody ReservationRequest request) {
        return reservationService.cancelReservation(request.toDomain());
    }
}