package ru.filatov.springrest.restApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.filatov.springrest.restApp.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
