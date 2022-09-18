package org.alexzhvalev.peopleinfoapplication.service;

import org.alexzhvalev.peopleinfoapplication.model.Person;

import java.util.List;

public interface PersonService {

    Person save (Person person);
    Person findById(int id);
    List<Person> findAll();
    void deleteById(int id);
    Person update(int id, Person person);


}
