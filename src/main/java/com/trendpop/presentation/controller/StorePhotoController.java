package com.trendpop.presentation.controller;

import com.trendpop.application.service.StorePhotoService;
import com.trendpop.presentation.dto.response.StorePhotoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/store-photos")
public class StorePhotoController {
    private final StorePhotoService storePhotoService;

    public StorePhotoController(StorePhotoService storePhotoService) {
        this.storePhotoService = storePhotoService;
    }

    @GetMapping("/{id}")
    public List<StorePhotoResponse> getStorePhotos(@PathVariable("id") String id) {
        return storePhotoService.getStorePhotos(id);
    }
}
