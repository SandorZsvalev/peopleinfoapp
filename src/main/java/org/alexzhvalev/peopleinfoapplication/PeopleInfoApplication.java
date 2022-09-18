package org.alexzhvalev.peopleinfoapplication;

import org.alexzhvalev.peopleinfoapplication.filecontroller.PersonFileController;
import org.alexzhvalev.peopleinfoapplication.filecontroller.PersonFileControllerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PeopleInfoApplication {

    public static void main(String[] args) {

        SpringApplication.run(PeopleInfoApplication.class);

 //       ApplicationContext appContext = SpringApplication.run(PeopleInfoApplication.class);
 //       PersonFileControllerImpl controller = appContext.getBean(PersonFileControllerImpl.class);

 //       controller.readFromFileAndInsertToDB();
    }
}
