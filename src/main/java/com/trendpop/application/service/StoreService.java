package com.trendpop.application.service;

import com.trendpop.application.service.util.StoreInfoUtil;
import com.trendpop.domain.model.Store;
import com.trendpop.domain.model.StoreInfo;
import com.trendpop.domain.model.StoreViewHistory;
import com.trendpop.exception.UninitializedException;
import com.trendpop.infrastructure.mapper.StoreMapper;
import com.trendpop.infrastructure.mapper.StoreViewHistoryMapper;
import com.trendpop.presentation.dto.response.StoreResponse;
import com.trendpop.presentation.dto.response.StoreViewHistoryResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreMapper storeMapper;
    private final StoreInfoUtil storeInfoUtil;
    private final StoreViewHistoryMapper storeViewHistoryMapper;

    public StoreService(StoreMapper storeMapper, StoreInfoUtil storeInfoUtil, StoreViewHistoryMapper storeViewHistoryMapper) {
        this.storeMapper = storeMapper;
        this.storeInfoUtil = storeInfoUtil;
        this.storeViewHistoryMapper = storeViewHistoryMapper;
    }

    public List<StoreResponse> getRecommendedStores() {
        List<String> storeIds = storeMapper.findActiveRecommendedStoreIdsOrderByPriority();
        StoreInfo storeInfo = storeInfoUtil.getStoreInfo(storeIds);
        return storeInfo.stores().stream()
                .map(store -> storeInfoUtil.mapToStoreResponse(store, storeInfo))
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
