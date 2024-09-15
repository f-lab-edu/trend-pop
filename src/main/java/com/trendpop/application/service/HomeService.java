package com.trendpop.application.service;

import com.trendpop.domain.model.Store;
import com.trendpop.infrastructure.mapper.HomeMapper;
import com.trendpop.presentation.dto.response.StoreResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private final HomeMapper homeMapper;

    public HomeService(HomeMapper homeMapper) {
        this.homeMapper = homeMapper;
    }

    public List<StoreResponse> getRecommendedStores() {
        List<Store> recommendedStores = homeMapper.findRecommendedStores();

        return recommendedStores.stream()
                .map(StoreResponse::from)
                .collect(Collectors.toList());
    }

    public List<StoreResponse> getMostPopularStores() {
        List<Store> popularStores = homeMapper.findMostPopularStores();

        return popularStores.stream()
                .map(StoreResponse::from)
                .collect(Collectors.toList());
    }

    public List<StoreResponse> getRecentlyViewedStores(String id) {
        List<Store> recentlyViewedStores = homeMapper.findRecentlyViewedStores(id);

        return recentlyViewedStores.stream()
                .map(StoreResponse::from)
                .collect(Collectors.toList());
    }
}
