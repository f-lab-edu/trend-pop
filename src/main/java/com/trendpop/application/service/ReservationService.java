package com.trendpop.application.service;

import com.trendpop.infrastructure.mapper.LocationMapper;
import com.trendpop.infrastructure.mapper.ReservationMapper;
import com.trendpop.infrastructure.mapper.StoreMapper;
import com.trendpop.infrastructure.mapper.StorePhotoMapper;
import com.trendpop.presentation.dto.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
}