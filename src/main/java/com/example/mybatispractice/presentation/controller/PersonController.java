package com.example.mybatispractice.presentation.controller;

import com.example.mybatispractice.application.service.PersonService;
import com.example.mybatispractice.presentation.dto.request.PersonRequest;
import com.example.mybatispractice.presentation.dto.response.PersonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/people")
public class PersonController {

  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping("/{id}")
  public PersonResponse viewPerson(@PathVariable("id") String id) {
    return personService.viewPerson(id);
  }

  @PostMapping
  public PersonResponse registerPerson(@RequestBody PersonRequest personRequest) {
    return personService.createPerson(personRequest.toDomain());
  }
}
