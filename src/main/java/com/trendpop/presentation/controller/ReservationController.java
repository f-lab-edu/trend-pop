package com.trendpop.presentation.controller;

import com.trendpop.application.service.ReservationService;
import com.trendpop.presentation.dto.response.StoreResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}