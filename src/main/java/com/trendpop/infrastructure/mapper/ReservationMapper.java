package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.Reservation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {
    @Select("""
            SELECT store_id FROM reservations WHERE status != 'CANCELLED'
            GROUP BY store_id ORDER BY COUNT(id) DESC LIMIT 10
            """)
    List<String> findTop10StoreIdsOrderByReservationCounts();

    @Insert("""
            INSERT INTO reservations (id, user_id, store_id, visit_at, visit_time, guest_count, status)
            VALUES (#{id}, #{userId}, #{storeId}, #{visitAt}, #{visitTime}, #{guestCount}, #{status})
            """)
    int createReservation(Reservation reservation);

    @Select("SELECT * FROM reservations WHERE user_id = #{userId}")
    List<Reservation> findReservationsByUserId(String userId);

    @Select("SELECT * FROM reservations WHERE id = #{id}")
    Reservation findReservationById(long id);

    @Update("""
            UPDATE reservations
            SET visit_at = #{visitAt}
              , visit_time = #{visitTime}
              , guest_count = #{guestCount}
              , updated_at = NOW()
            WHERE id = #{id}
            """)
    int updateReservationInfo(Reservation reservation);

    @Update("""
            UPDATE reservations
            SET status = #{status}
              , updated_at = NOW()
            WHERE id = #{id}
            """)
    int updateReservationStatus(Reservation reservation);

}
