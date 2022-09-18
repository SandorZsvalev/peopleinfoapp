package org.alexzhvalev.peopleinfoapplication.exception;

public class PersonNotFoundException extends RuntimeException{
    private final static String MESSAGE = "Person not found";

    public PersonNotFoundException () {
        super(MESSAGE);
    }

}
