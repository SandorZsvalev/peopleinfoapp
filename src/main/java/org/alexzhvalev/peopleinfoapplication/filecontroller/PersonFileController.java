package org.alexzhvalev.peopleinfoapplication.filecontroller;

public interface PersonFileController {
    void writeIntoFileFromDB(int id);
    void readFromFileAndInsertToDB();

}
