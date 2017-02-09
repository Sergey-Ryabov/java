package com.testtask.haulmont.dao.hsqldb;

import com.testtask.haulmont.dao.StudentDao;
import com.testtask.haulmont.model.Group;
import com.testtask.haulmont.model.Student;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Сергей
 */
public class HsqlStudentDao implements StudentDao {

    private final Connection connection;
    private HsqlGroupDao groupDao;

    public HsqlStudentDao(Connection connection) {
        this.connection = connection;
        groupDao = new HsqlGroupDao(connection);
    }

    @Override
    public Student createStudent(String name, String surname, String patronymic,
            Date birthday, Group group) throws SQLException {
        String query = "INSERT INTO student (name, surname, patronymic, birthday, groupId)"
                + " VALUES('" + name
                + "', '" + surname
                + "', '" + patronymic
                + "', '" + birthday
                + "'," + group.getId() + ")";
        Student student;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            query = "SELECT id, name, surname, patronymic, birthday, groupId "
                    + "FROM student WHERE id = IDENTITY();";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            student = new Student(resultSet.getLong(1),
                    resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getDate(5),
                    groupDao.read(resultSet.getLong(6)));
            statement.close();
        }
        return student;
    }

    @Override
    public Student read(long id) throws SQLException {
        String query = "SELECT id, name, surname, patronymic, birthday, groupId"
                + " FROM student WHERE id = " + id;
        Student student;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            student = new Student(resultSet.getLong(1),
                    resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getDate(5),
                    groupDao.read(resultSet.getLong(6)));
            statement.close();
        }
        return student;
    }

    @Override
    public void update(Student student) throws SQLException {
        String query = "UPDATE student SET name = '" + student.getName()
                + "', surname = '" + student.getSurname()
                + "', patronymic = '" + student.getPatronymic()
                + "', birthday = '" + student.getBirthday()
                + "', groupId = " + student.getGroup().getId()
                + "WHERE id = " + student.getId();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            statement.close();
        }
    }

    @Override
    public void delete(Student student) throws SQLException {
        String query = "DELETE FROM student WHERE id = " + student.getId();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            statement.close();
        }
    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> students = new LinkedList<>();
        String query = "SELECT id, name, surname, patronymic, birthday, groupId FROM student";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                students.add(new Student(resultSet.getLong(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5),
                        groupDao.read(resultSet.getLong(6))));
            }
            statement.close();
        }
        return students;
    }

}
