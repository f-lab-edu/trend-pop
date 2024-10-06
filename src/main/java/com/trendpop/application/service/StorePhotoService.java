package com.trendpop.application.service;

import com.trendpop.domain.model.StorePhoto;
import com.trendpop.infrastructure.mapper.StorePhotoMapper;
import com.trendpop.presentation.dto.response.StorePhotoResponse;
import com.trendpop.presentation.dto.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorePhotoService {
    private final StorePhotoMapper storePhotoMapper;

    public List<StorePhotoResponse> getStorePhotos(String id) {
        List<StorePhoto> storePhotos = storePhotoMapper.findStorePhotosByStoreId(id);

        return storePhotos.stream()
                .map(StorePhotoResponse::from)
                .collect(Collectors.toList());
    }

}
