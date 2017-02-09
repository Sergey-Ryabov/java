package com.testtask.haulmont.dao;

import com.testtask.haulmont.model.Group;
import com.testtask.haulmont.model.Student;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Сергей
 */
public interface StudentDao {

    Student createStudent(String name, String surname, String patronymic,
            Date birthday, Group group) throws SQLException;

    Student read(long id) throws SQLException;

    void update(Student student) throws SQLException;

    void delete(Student student) throws SQLException;

    List<Student> getAll() throws SQLException;

}
