package org.alexzhvalev.peopleinfoapplication.exception;

import java.sql.SQLException;

public class SqlProcessingException extends RuntimeException {

    public SqlProcessingException(String message) {
        super(message);
    }
}
