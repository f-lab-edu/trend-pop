package com.example.mybatispractice.presentation.dto.response;

import com.example.mybatispractice.domain.model.Person;

public record PersonResponse(String id, String name, int age, String address) {

  public static PersonResponse from(Person person) {
    return new PersonResponse(person.id(), person.name(), person.age(), person.address());
  }
}
