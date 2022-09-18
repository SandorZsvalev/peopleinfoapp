package org.alexzhvalev.peopleinfoapplication.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class PostgressConnector implements DatabaseConnector {

    @Value("${dbConnector.url}")
    private String url;
    @Value("${dbConnector.user}")
    private String user;
    @Value("${dbConnector.password}")
    private String password;

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
