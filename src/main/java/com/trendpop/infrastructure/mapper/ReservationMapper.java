package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.StoreReservationCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReservationMapper {
    @Select("""
            SELECT store_id, count(id) AS reservation_count FROM reservations WHERE deleted = FALSE AND status != 'CANCELLED'
            GROUP BY store_id ORDER BY COUNT(id) DESC LIMIT 10
            """)
    List<StoreReservationCount> findTop10ActiveStoreReservationCounts();
}
