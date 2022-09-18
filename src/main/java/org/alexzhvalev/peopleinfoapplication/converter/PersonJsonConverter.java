package org.alexzhvalev.peopleinfoapplication.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.alexzhvalev.peopleinfoapplication.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonJsonConverter {

    public Person fromJsonToPerson (String json) {
        try {
            return createMapper().readValue(json,Person.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String fromPersonToJson (Person person) {
        try {
            return createMapper().writeValueAsString(person);
        } catch (JsonProcessingException e) {
           throw new RuntimeException(e.getMessage());
        }
    }

    private ObjectMapper createMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
