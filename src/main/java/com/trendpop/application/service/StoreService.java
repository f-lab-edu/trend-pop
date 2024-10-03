package com.trendpop.application.service;

import com.trendpop.domain.model.Location;
import com.trendpop.domain.model.Store;
import com.trendpop.domain.model.StoreViewHistory;
import com.trendpop.infrastructure.mapper.LocationMapper;
import com.trendpop.infrastructure.mapper.StoreMapper;
import com.trendpop.infrastructure.mapper.StorePhotoMapper;
import com.trendpop.infrastructure.mapper.StoreViewHistoryMapper;
import com.trendpop.presentation.dto.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;
    private final StorePhotoMapper storePhotoMapper;
    private final LocationMapper locationMapper;
    private final StoreViewHistoryMapper storeViewHistoryMapper;

    StoreResponse createStoreResponse(String storeId) {
        Integer minOrder = storePhotoMapper.findMinOrderByStoreId(storeId);

        String imageUrl = (minOrder != null)
                ? storePhotoMapper.findImageUrlByStoreIdAndOrder(storeId, minOrder).imageUrl()
                : null;

        Store store = storeMapper.findStoreById(storeId);

        Location location = locationMapper.findLocationById(store.locationId());

        return StoreResponse.from(store, location, imageUrl);
    }
    public List<StoreResponse> getRecommendedStores() {
        List<String> storeIds = storeMapper.findRecommendedStoreIdsOrderByPriority();

        return storeIds.stream()
                .map(this::createStoreResponse)
                .collect(Collectors.toList());
    }

    public List<StoreResponse> getRecentlyViewedStores(String id) {
        List<StoreViewHistory> recentlyViewedStores = storeViewHistoryMapper.findRecentlyViewedStoresByUserId(id);
        List<String> storeIds = recentlyViewedStores.stream().map(StoreViewHistory::storeId).toList();

        return storeIds.stream()
                .map(this::createStoreResponse)
                .collect(Collectors.toList());
    }
}
