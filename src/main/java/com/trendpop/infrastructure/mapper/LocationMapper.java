package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LocationMapper {

    @Select("SELECT * FROM locations WHERE id = #{locationId} AND deleted = FALSE")
    Location findLocationById(String locationId);
}
