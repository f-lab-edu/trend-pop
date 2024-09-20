package com.trendpop.application.service;

import com.trendpop.application.service.util.StoreInfoUtil;
import com.trendpop.domain.model.*;
import com.trendpop.exception.UninitializedException;
import com.trendpop.infrastructure.mapper.*;
import com.trendpop.presentation.dto.response.ReservationCountResponse;
import com.trendpop.presentation.dto.response.StoreResponse;
import com.trendpop.presentation.dto.response.StoreViewHistoryResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private final StoreMapper storeMapper;
    private final StoreInfoUtil storeInfoUtil;
    private final ReservationMapper reservationMapper;
    private final StoreViewHistoryMapper storeViewHistoryMapper;

    public HomeService(StoreMapper storeMapper, StoreInfoUtil storeInfoUtil, ReservationMapper reservationMapper, StoreViewHistoryMapper storeViewHistoryMapper) {
        this.storeMapper = storeMapper;
        this.storeInfoUtil = storeInfoUtil;
        this.reservationMapper = reservationMapper;
        this.storeViewHistoryMapper = storeViewHistoryMapper;
    }

    public List<StoreResponse> getRecommendedStores() {
        List<String> storeIds = storeMapper.findRecommendedStoreIds();
        StoreInfo storeInfo = storeInfoUtil.getStoreInfo(storeIds);
        return storeInfo.stores().stream()
                                 .map(store -> storeInfoUtil.mapToStoreResponse(store, storeInfo))
                                 .collect(Collectors.toList());
    }

    public List<ReservationCountResponse> getMostPopularStores() {
        List<StoreReservationCount> top10StoreReservationCounts = reservationMapper.findTop10StoreReservationCounts();
        List<String> top10StoreIds = top10StoreReservationCounts.stream()
                                                                .map(StoreReservationCount::storeId).toList();
        StoreInfo storeInfo = storeInfoUtil.getStoreInfo(top10StoreIds);

        return top10StoreReservationCounts.stream()
                .map(reservationCount -> {
                    Store store = storeInfo.stores().stream()
                                                    .filter(s -> s.id().equals(reservationCount.storeId()))
                                                    .findFirst().orElseThrow(() -> new UninitializedException("STORE"));
                    return ReservationCountResponse.from(store, storeInfoUtil.mapToStoreResponse(store, storeInfo), reservationCount);
                })
                .sorted(Comparator.comparingInt(ReservationCountResponse::reservationCount).reversed())
                .collect(Collectors.toList());
    }

    public List<StoreViewHistoryResponse> getRecentlyViewedStores(String id) {
        List<StoreViewHistory> recentlyViewedStores = storeViewHistoryMapper.findRecentlyViewedStoresByUserId(id);
        List<String> storeIds = recentlyViewedStores.stream()
                .map(StoreViewHistory::storeId).collect(Collectors.toList());
        StoreInfo storeInfo = storeInfoUtil.getStoreInfo(storeIds);

        return recentlyViewedStores.stream()
                .map(storeView -> {
                    Store store = storeInfo.stores().stream()
                                                    .filter(s -> s.id().equals(storeView.storeId()))
                                                    .findFirst().orElseThrow(() -> new UninitializedException("STORE"));
                    return StoreViewHistoryResponse.from(store, storeInfoUtil.mapToStoreResponse(store, storeInfo), storeView);
                })
                .sorted(Comparator.comparing(StoreViewHistoryResponse::viewedAt).reversed())
                .collect(Collectors.toList());
    }
}

