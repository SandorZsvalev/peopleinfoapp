package org.alexzhvalev.peopleinfoapplication.dao;

import org.alexzhvalev.peopleinfoapplication.exception.PersonNotFoundException;
import org.alexzhvalev.peopleinfoapplication.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootTest
class PersonDaoImplTest {

    private static final String CREATE_TABLE = "CREATE TABLE person (" +
            "id serial primary key, first_name text, last_name text, date_of_birth date, age integer" +
            ");";

    @Autowired
    private PersonDao personDao;

/*
    @BeforeAll
    static void init() throws SQLException {
        DatabaseConnector databaseConnector = new H2Connector();
        createTables(databaseConnector.getConnection());
        personDao = new PersonDaoImpl(databaseConnector);
    }
*/

    @BeforeEach
    void deleteAll() {
        personDao.deleteAll();
    }

    @Test
    void saveAndFindTest() {
        Person personForSave = TestDataProvider.buildPerson(1);
        int id = personDao.save(personForSave).getId();
        Person foundPerson = personDao.findById(id);
        Assertions.assertEquals(personForSave, foundPerson);
    }

    @Test
    void saveAndFindAllTest() {
        personDao.save(TestDataProvider.buildPerson(1));
        personDao.save(TestDataProvider.buildPerson(2));
        Assertions.assertEquals(2, personDao.findAll().size());
    }

    @Test
    void saveAndDeleteTest() {
        int id = personDao.save(TestDataProvider.buildPerson(1)).getId();
        personDao.deleteById(id);
        Assertions.assertEquals(0, personDao.findAll().size());

        Assertions.assertThrows(PersonNotFoundException.class, () -> personDao.deleteById(id));
    }

    @Test
    void saveAndUpdateTest() {
        Person savedPerson = personDao.save(TestDataProvider.buildPerson(1));
        int id = savedPerson.getId();
        String upd = "UpdatedName";
        savedPerson.setFirstName(upd);
        personDao.updateById(id, savedPerson);
        Person updatedPerson = personDao.findById(id);
        Assertions.assertEquals(savedPerson, updatedPerson);
    }

    private static void createTables(Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE);
        preparedStatement.execute();

    }


    //TODO сделать тесты для сервиса - надо сделать привязку к проверке возраста

}