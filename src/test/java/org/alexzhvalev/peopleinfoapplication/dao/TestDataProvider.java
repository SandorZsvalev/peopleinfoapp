package org.alexzhvalev.peopleinfoapplication.dao;

import org.alexzhvalev.peopleinfoapplication.model.Person;

import java.time.LocalDate;

public class TestDataProvider {

    public static Person buildPerson(int i){
     return new Person(
             "TestFirstName"+i,
             "TestName"+i,
             LocalDate.of(1995,10,5).plusDays(i));
    }

}
