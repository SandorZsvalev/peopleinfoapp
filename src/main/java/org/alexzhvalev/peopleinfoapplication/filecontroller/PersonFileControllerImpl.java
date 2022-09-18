package org.alexzhvalev.peopleinfoapplication.filecontroller;

import org.alexzhvalev.peopleinfoapplication.converter.PersonJsonConverter;
import org.alexzhvalev.peopleinfoapplication.model.Person;
import org.alexzhvalev.peopleinfoapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class PersonFileControllerImpl implements PersonFileController{

    private PersonService service;
    private PersonJsonConverter converter;

    @Autowired
    public PersonFileControllerImpl(PersonService service, PersonJsonConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    //TODO дообработать исключения
    @Override
    public void writeIntoFileFromDB(int id) {
       Person person = service.findById(id);
       String personAsJson = converter.fromPersonToJson(person);
        try (Writer writer = new FileWriter("src/main/resources/output_person.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer)){
            bufferedWriter.write(personAsJson);
        } catch (FileNotFoundException e) {
            System.out.println("Файл output_person не найден");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readFromFileAndInsertToDB() {
        StringBuilder personString = new StringBuilder();
        try (Reader reader = new FileReader("src/main/resources/input_person.txt");
             BufferedReader bufferedReader = new BufferedReader(reader)){
            while (bufferedReader.ready()){
                personString.append(bufferedReader.readLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e.getMessage());
        } catch (IOException e){
            System.out.println("File reading error");
            throw new RuntimeException(e.getMessage());
        }

        Person person = converter.fromJsonToPerson(personString.toString());
        service.save(person);
    }
}
