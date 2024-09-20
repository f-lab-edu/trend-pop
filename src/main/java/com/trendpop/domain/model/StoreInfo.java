package com.trendpop.domain.model;

import java.util.List;

public record StoreInfo(List<Store> stores, List<StoreType> storeTypes, List<Location> locations, List<StorePhoto> storePhotos) {
}
