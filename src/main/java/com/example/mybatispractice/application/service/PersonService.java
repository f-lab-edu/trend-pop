package com.example.mybatispractice.application.service;

import com.example.mybatispractice.domain.model.Person;
import com.example.mybatispractice.infrastructure.mapper.PersonMapper;
import com.example.mybatispractice.presentation.dto.response.PersonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

  private final PersonMapper personMapper;

  public PersonService(PersonMapper personMapper) {
    this.personMapper = personMapper;
  }

  public PersonResponse viewPerson(String id) {
    Person person = personMapper.find(id);
    return PersonResponse.from(person);
  }

  @Transactional
  public PersonResponse createPerson(Person person) {
    personMapper.create(person);
    Person foundPerson = personMapper.find(person.id());
    return PersonResponse.from(foundPerson);
  }
}
