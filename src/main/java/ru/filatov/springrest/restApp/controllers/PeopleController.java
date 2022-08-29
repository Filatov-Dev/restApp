package ru.filatov.springrest.restApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.filatov.springrest.restApp.dto.PersonDTO;
import ru.filatov.springrest.restApp.models.Person;
import ru.filatov.springrest.restApp.services.PeopleService;
import ru.filatov.springrest.restApp.util.PersonErrorResponse;
import ru.filatov.springrest.restApp.util.PersonNotCreatedException;
import ru.filatov.springrest.restApp.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPeople() {
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getperson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handelException(PersonNotFoundException foundException){
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this ID wasn't found",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            StringBuilder errMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for(FieldError error: errors) {
                errMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errMsg.toString());
        }
        peopleService.save(convertToPerson(personDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Person convertToPerson(PersonDTO personDto) {
        return modelMapper.map(personDto, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handelException(PersonNotCreatedException createdException){
        PersonErrorResponse response = new PersonErrorResponse(
                createdException.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
