package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.RecommendedStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecommendedStoreMapper {
    @Select("SELECT * FROM recommended_stores WHERE deleted = FALSE ORDER BY priority")
    List<RecommendedStore> findRecommendedStoreIdsOrderByPriority();
}
