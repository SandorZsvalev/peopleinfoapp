package org.alexzhvalev.peopleinfoapplication.controller;

import org.alexzhvalev.peopleinfoapplication.model.Person;
import org.alexzhvalev.peopleinfoapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping ("/api/v1/people")
    public List<Person> findAllPeople (){
        return personService.findAll();
    }

    @GetMapping ("/api/v1/people/{id}")
    public Person findPersonById (@PathVariable(name = "id")int id) {
        return personService.findById(id);
    }

    @PostMapping ("/api/v1/people")
    public Person savePerson (@RequestBody Person person){
        return personService.save(person);
    }

    @DeleteMapping ("/api/v1/people/{id}")
    public void deletePersonById (@PathVariable(name = "id")int id){
        personService.deleteById(id);
    }

    @PutMapping("/api/v1/people/{id}")
    public Person updatePerson (@PathVariable (name = "id") int id, @RequestBody Person person){
        return personService.update(id, person);
    }

}
