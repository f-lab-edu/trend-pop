package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.StoreViewHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreViewHistoryMapper {
    @Select("""
            SELECT store_id, MAX(viewed_at) AS viewed_at
            FROM store_view_history
            WHERE user_id = #{userId}
            GROUP BY store_id
            ORDER BY viewed_at DESC
            LIMIT 10
            """)
    List<StoreViewHistory> findRecentlyViewedStoresByUserId(@Param("userId") String userId);
}
