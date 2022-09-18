package org.alexzhvalev.peopleinfoapplication.dao;

import org.alexzhvalev.peopleinfoapplication.model.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonDao {

    List <Person> findAll();

    Person findById(int id);

    Person save(Person person);

    void deleteById (int id);

    Person updateById (int id, Person person);

    void deleteAll ();

}
