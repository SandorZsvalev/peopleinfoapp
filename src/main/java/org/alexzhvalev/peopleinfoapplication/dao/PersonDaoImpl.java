package org.alexzhvalev.peopleinfoapplication.dao;

import org.alexzhvalev.peopleinfoapplication.exception.PersonNotFoundException;
import org.alexzhvalev.peopleinfoapplication.exception.SqlProcessingException;
import org.alexzhvalev.peopleinfoapplication.model.Person;
import org.alexzhvalev.peopleinfoapplication.util.DatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

// CRUD - выполняет только эти операции
// Repository == Component
@Repository
public class PersonDaoImpl implements PersonDao {

    private final static int ID_NOT_FOUND = -1;
    private final static String FIND_ALL = "SELECT * FROM person;";
    private final static String FIND_BY_ID = "SELECT * FROM person WHERE id = ?;";
    private final static String INSERT_INTO_PERSON = "INSERT INTO person (first_name, last_name, age, date_of_birth) " +
            "VALUES (?,?,?,?);";
    private final static String DELETE_BY_ID = "DELETE FROM person WHERE id = ?;";
    private final static String UPDATE_BY_ID = "UPDATE person SET first_name = ?, last_name = ?, age = ?, date_of_birth = ? WHERE id = ?;";
    private final static String DELETE_ALL = "DELETE FROM person;";

    private final DatabaseConnector connector;

    @Autowired
    public PersonDaoImpl(DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public List<Person> findAll() {
        List<Person> result = new LinkedList<>();
        // try with resources
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(buildPerson(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new SqlProcessingException(e.getMessage());
        }
    }

    @Override
    public Person findById(int id) {
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return buildPerson(resultSet);
            } else {
                throw new PersonNotFoundException();
            }
        } catch (SQLException e) {
            throw new SqlProcessingException(e.getMessage());
        }
    }

    @Override
    public Person save (Person person) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_PERSON,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1,person.getFirstName());
            preparedStatement.setString(2,person.getLastName());
            preparedStatement.setInt(3,person.getAge());
            preparedStatement.setDate(4,java.sql.Date.valueOf(person.getDateOfBirth()));
            preparedStatement.executeUpdate();

            person.setId(returningIdAfterSave(preparedStatement.getGeneratedKeys()));

            return person;
        } catch (SQLException e){
            throw new SqlProcessingException(e.getMessage());
        }
    }

    @Override
    // обычно делет возвращает войд
    public void deleteById(int id) {
        // проверяем, есть ли такая запись
        findById(id);
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SqlProcessingException(e.getMessage());
        }
    }

    @Override
    public Person updateById(int id, Person person) {
        // проверяем, есть ли такая запись
        findById(id);

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setDate(4, java.sql.Date.valueOf(person.getDateOfBirth()));
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();

            person.setId(id);
            return person;
        } catch (SQLException e) {
            throw new SqlProcessingException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlProcessingException(e.getMessage());
        }
    }

    private Person buildPerson (ResultSet resultSet) {
        Person person = new Person();
        try {
        person.setId(resultSet.getInt("id"));
        person.setFirstName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        person.setAge(resultSet.getInt("age"));
        person.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
        //new java.sql.Date(resultSet.getDate("date_of_birth").getTime()).toLocalDate()
        return person;
        } catch (SQLException e) {
            throw new SqlProcessingException(e.getMessage());
        }
    }

    private int returningIdAfterSave(ResultSet generatedKeys) {
        try {
            generatedKeys.next();
            return generatedKeys.getInt(1);

        } catch (SQLException e) {
            throw new SqlProcessingException(e.getMessage());
        }
    }

}
