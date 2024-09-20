package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.Stores;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface HomeMapper { // HomeMapper 삭제 예정.

    @Select("""
            SELECT s.id AS store_id, s.name AS store_name, s.description, s.start_at, s.end_at,
            	   st.id AS store_type_id, st.name AS store_type_name,
            	   l.id AS location_id, l.name AS location_name, sp.image_url
            FROM recommended_stores rs
            LEFT JOIN stores s ON rs.store_id = s.id
            LEFT JOIN store_types st ON s.type = st.id
            LEFT JOIN locations l ON s.location_id = l.id
            LEFT JOIN (
            	SELECT sp.store_id, sp.image_url, sp.`order`
            	FROM store_photos sp
            	INNER JOIN (
            		SELECT store_id, MIN(`order`) AS min_order
            		FROM store_photos
            		WHERE deleted = FALSE
            		GROUP BY store_id
            	) min_sp ON sp.store_id = min_sp.store_id AND sp.`order` = min_sp.min_order
            ) sp ON s.id = sp.store_id
            WHERE rs.is_active = TRUE
            AND s.deleted = FALSE
            ORDER BY rs.priority
            """)
    List<Stores> findRecommendedStores();

    @Select("""
            SELECT s.id AS store_id, s.name AS store_name, s.description, s.start_at, s.end_at,
                   st.id AS store_type_id, st.name AS store_type_name,
                   l.id as location_id, l.name AS location_name,
                   sp.image_url, COUNT(r.id) AS r_cnt
            FROM stores s
            LEFT JOIN reservations r ON s.id = r.store_id
            LEFT JOIN store_types st ON s.type = st.id
            LEFT JOIN reservation_status_codes rsc ON r.status = rsc.code
            LEFT JOIN locations l ON s.location_id = l.id
            LEFT JOIN (
                SELECT sp.store_id, sp.image_url, sp.`order`
            	FROM store_photos sp
            	INNER JOIN (
            		SELECT store_id, MIN(`order`) AS min_order
            		FROM store_photos
            		WHERE deleted = FALSE
            		GROUP BY store_id
            	) min_sp ON sp.store_id = min_sp.store_id AND sp.`order` = min_sp.min_order
            	WHERE sp.deleted = FALSE
            ) sp ON s.id = sp.store_id
            WHERE s.deleted = FALSE
            AND r.deleted = FALSE
            AND rsc.code != 'CANCELLED'
            GROUP BY s.id, s.name, s.description, s.start_at, s.end_at, st.id, st.name, l.id, l.name, sp.image_url
            ORDER BY r_cnt DESC
            LIMIT 10
            """)
    List<Stores> findMostPopularStores();

    @Select("""
            SELECT s.id AS store_id, s.name AS store_name, s.description, s.start_at, s.end_at,
            	   st.id AS store_type_id, st.name AS store_type_name,
            	   l.id AS location_id, l.name AS location_name, sp.image_url
            FROM stores s
            INNER JOIN (
                SELECT sv.store_id, MAX(sv.viewed_at) AS viewed_at
                FROM store_view_history sv
                WHERE sv.user_id = #{id}
                GROUP BY sv.store_id
            ) sv_max ON s.id = sv_max.store_id
            INNER JOIN store_view_history sv ON s.id = sv.store_id AND sv.viewed_at = sv_max.viewed_at
            LEFT JOIN store_types st ON s.type = st.id
            LEFT JOIN locations l ON s.location_id = l.id
            LEFT JOIN (
            	SELECT sp.store_id, sp.image_url, sp.`order`
            	FROM store_photos sp
            	INNER JOIN (
            		SELECT store_id, MIN(`order`) AS min_order
            		FROM store_photos
            		WHERE deleted = FALSE
            		GROUP BY store_id
            	) min_sp ON sp.store_id = min_sp.store_id AND sp.`order` = min_sp.min_order
            ) sp ON s.id = sp.store_id
            ORDER BY sv.viewed_at DESC
            LIMIT 10
            """)
    List<Stores> findRecentlyViewedStores(String id);
}
