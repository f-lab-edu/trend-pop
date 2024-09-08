package com.example.mybatispractice.presentation.dto.request;

import com.example.mybatispractice.domain.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {

    private String id;
    private String name;
    private int age;
    private String address;

    public Person toDomain() {
      return new Person(id, name, age, address);
    }
}
