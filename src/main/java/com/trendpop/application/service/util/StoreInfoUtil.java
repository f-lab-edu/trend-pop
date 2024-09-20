package com.trendpop.application.service.util;

import com.trendpop.domain.model.*;
import com.trendpop.infrastructure.mapper.LocationMapper;
import com.trendpop.infrastructure.mapper.StoreMapper;
import com.trendpop.infrastructure.mapper.StorePhotoMapper;
import com.trendpop.infrastructure.mapper.StoreTypeMapper;
import com.trendpop.presentation.dto.response.StoreResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreInfoUtil {

    private final StoreMapper storeMapper;
    private final StoreTypeMapper storeTypeMapper;
    private final LocationMapper locationMapper;
    private final StorePhotoMapper storePhotoMapper;

    public StoreInfoUtil(StoreMapper storeMapper, StoreTypeMapper storeTypeMapper, LocationMapper locationMapper, StorePhotoMapper storePhotoMapper) {
        this.storeMapper = storeMapper;
        this.storeTypeMapper = storeTypeMapper;
        this.locationMapper = locationMapper;
        this.storePhotoMapper = storePhotoMapper;
    }

    public StoreInfo getStoreInfo(List<String> storeIds) {
        List<Store> stores = storeMapper.findStoresByIds(storeIds);
        List<String> storeTypeIds = stores.stream().map(Store::type).distinct().collect(Collectors.toList());
        List<StoreType> storeTypes = storeTypeMapper.findStoreTypesByIds(storeTypeIds);

        List<String> locationIds = stores.stream().map(Store::locationId).distinct().collect(Collectors.toList());
        List<Location> locations = locationMapper.findLocationsByIds(locationIds);

        List<StorePhoto> mainStorePhotos = storePhotoMapper.findMainStorePhotosByIds(storeIds);

        return new StoreInfo(stores, storeTypes, locations, mainStorePhotos);
    }

    public StoreResponse mapToStoreResponse(Store store, StoreInfo storeInfo) {
        StoreType storeType = storeInfo.storeTypes().stream()
                                                    .filter(st -> st.id().equals(store.type()))
                                                    .findFirst()
                                                    .orElse(null);

        Location location = storeInfo.locations().stream()
                                                 .filter(l -> l.id().equals(store.locationId()))
                                                 .findFirst()
                                                 .orElse(null);

        StorePhoto storePhoto = storeInfo.storePhotos().stream()
                                                       .filter(sp -> sp.storeId().equals(store.id()))
                                                       .findFirst()
                                                       .orElse(null);

        return StoreResponse.from(store, storeType, location, storePhoto);
    }
}
