package com.trendpop.application.service;

import com.trendpop.application.service.util.StoreInfoUtil;
import com.trendpop.domain.model.StoreInfo;
import com.trendpop.domain.model.StoreReservationCount;
import com.trendpop.exception.UninitializedException;
import com.trendpop.infrastructure.mapper.ReservationMapper;
import com.trendpop.presentation.dto.response.ReservationCountResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final StoreInfoUtil storeInfoUtil;

    public ReservationService(ReservationMapper reservationMapper, StoreInfoUtil storeInfoUtil) {
        this.reservationMapper = reservationMapper;
        this.storeInfoUtil = storeInfoUtil;
    }

    public List<ReservationCountResponse> getMostPopularStores() {
        List<StoreReservationCount> top10StoreReservationCounts = reservationMapper.findTop10ActiveStoreReservationCounts();
        List<String> top10StoreIds = top10StoreReservationCounts.stream()
                .map(StoreReservationCount::storeId).toList();
        StoreInfo storeInfo = storeInfoUtil.getStoreInfo(top10StoreIds);

        return top10StoreReservationCounts.stream()
                .map(reservationCount -> {
                    var store = storeInfo.stores().stream()
                            .filter(s -> s.id().equals(reservationCount.storeId()))
                            .findFirst().orElseThrow(() -> new UninitializedException("STORE"));
                    return ReservationCountResponse.from(store, storeInfoUtil.mapToStoreResponse(store, storeInfo), reservationCount);
                })
                .sorted(Comparator.comparingInt(ReservationCountResponse::reservationCount).reversed())
                .collect(Collectors.toList());
    }
}