package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LocationMapper {

    @Select("<script>" +
            "SELECT * FROM locations WHERE id IN " +
            "<foreach item='locationId' collection='locationIds' open='(' separator=',' close=')'>" +
            "#{locationId}" +
            "</foreach>" +
            "</script>")
    List<Location> findLocationsByIds(@Param("locationIds") List<String> locationIds);
}
