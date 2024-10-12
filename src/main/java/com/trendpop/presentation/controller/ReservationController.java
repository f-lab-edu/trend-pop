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
    public List<StoreResponse> getMostPopularStores() {
        return reservationService.getMostPopularStores();
    }

    @PostMapping("/create")
    public ReservationResponse createReservation(@RequestBody ReservationRequest request) {
        return reservationService.createReservation(request.toDomain());
    }

    @GetMapping("/{userId}")
    public List<ReservationResponse> getReservationsByUserId(@PathVariable String userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @PatchMapping("/update")
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