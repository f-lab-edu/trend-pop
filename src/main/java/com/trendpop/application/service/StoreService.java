package com.trendpop.application.service;

import com.trendpop.domain.model.*;
import com.trendpop.infrastructure.mapper.*;
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
    private final RecommendedStoreMapper recommendedStoreMapper;

    StoreResponse createStoreResponse(String storeId) {
        StorePhoto storePhoto = storePhotoMapper.findMinOrderByStoreId(storeId);

        String imageUrl = (storePhoto != null)
                ? storePhotoMapper.findImageUrlByStoreIdAndOrder(storeId, storePhoto.order()).imageUrl()
                : null;

        Store store = storeMapper.findStoreById(storeId);

        Location location = locationMapper.findLocationById(store.locationId());

        return StoreResponse.from(store, location, imageUrl);
    }
    public List<StoreResponse> getRecommendedStores() {
        List<RecommendedStore> recommendedStores = recommendedStoreMapper.findRecommendedStoreIdsOrderByPriority();
        List<String> storeIds = recommendedStores.stream().map(RecommendedStore::storeId).toList();

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
