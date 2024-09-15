package com.trendpop.presentation.controller;

import com.trendpop.application.service.HomeService;
import com.trendpop.presentation.dto.response.StoreResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/recommended-stores")
    public List<StoreResponse> getRecommendedStores() {
        return homeService.getRecommendedStores();
    }

    @GetMapping("/most-popular-stores")
    public List<StoreResponse> getMostPopularStores() {
        return homeService.getMostPopularStores();
    }

    @GetMapping("/recently-viewed-stores/{id}")
    public List<StoreResponse> getRecentlyViewedStores(@PathVariable("id") String id) {
        return homeService.getRecentlyViewedStores(id);
    }
}