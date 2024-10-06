package com.trendpop.presentation.controller;

import com.trendpop.application.service.StoreService;
import com.trendpop.presentation.dto.response.StoreDetailResponse;
import com.trendpop.presentation.dto.response.StoreResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/recommended")
    public List<StoreResponse> getRecommendedStores() {
        return storeService.getRecommendedStores();
    }

    @GetMapping("/recently-viewed/{id}")
    public List<StoreResponse> getRecentlyViewedStores(@PathVariable("id") String id) {
        return storeService.getRecentlyViewedStores(id);
    }

    @GetMapping("/{id}")
    public StoreDetailResponse getStoreInfo(@PathVariable("id") String id) {
        return storeService.getStoreInfo(id);
    }
}