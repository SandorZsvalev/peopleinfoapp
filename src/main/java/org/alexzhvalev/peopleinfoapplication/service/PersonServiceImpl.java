package org.alexzhvalev.peopleinfoapplication.service;

import org.alexzhvalev.peopleinfoapplication.dao.PersonDao;
import org.alexzhvalev.peopleinfoapplication.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
//Service == Component
public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public Person save(Person person) {
        person.setAge(calculateAge(person.getDateOfBirth()));
        return personDao.save(person);
    }

    @Override
    public Person findById(int id) {
        return personDao.findById(id);
    }

    @Override
    public List<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    public void deleteById(int id) {
        personDao.deleteById(id);
    }

    @Override
    public Person update(int id, Person person) {
        person.setAge(calculateAge(person.getDateOfBirth()));
        return personDao.updateById(id, person);
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
