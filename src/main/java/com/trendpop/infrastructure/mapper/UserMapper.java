package com.trendpop.infrastructure.mapper;

import com.trendpop.domain.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{id}")
    User find(String id);

    @Insert("INSERT INTO users (id, email, password, name) values (#{id}, #{email}, #{password}, #{name})")
    int create(User user);
}
