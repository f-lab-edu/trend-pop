package com.trendpop.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReservationMapper {
    @Select("""
            SELECT store_id FROM reservations WHERE status != 'CANCELLED'
            GROUP BY store_id ORDER BY COUNT(id) DESC LIMIT 10
            """)
    List<String> findTop10StoreIdsOrderByReservationCounts();
}
