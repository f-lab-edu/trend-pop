package com.example.mybatispractice.infrastructure.mapper;

import com.example.mybatispractice.domain.model.Person;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PersonMapper {

  // @Select(SELECT * FROM person WHERE id = #{id}") 으로 작성 가능. (보통 복잡한 쿼리만 xml 작성)
  Person find(String id);

  int create(Person person);
}
