package com.testtask.haulmont.dao.hsqldb;

import com.testtask.haulmont.dao.GroupDao;
import com.testtask.haulmont.model.Group;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Сергей
 */
public class HsqlGroupDao implements GroupDao {

    private final Connection connection;

    public HsqlGroupDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Group createGroup(int number, String facultyName) throws SQLException {
        String query = "INSERT INTO groups (number, facultyName) VALUES("
                + number + ",'" + facultyName + "')";
        Group group;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            query = "SELECT id, number, facultyName FROM groups WHERE id = IDENTITY();";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            group = new Group(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3));
            statement.close();
        }
        return group;
    }

    @Override
    public Group read(long id) throws SQLException {
        String query = "SELECT id, number, facultyName FROM groups WHERE id = " + id;
        Group group;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            group = new Group(resultSet.getLong(1), resultSet.getInt(2), resultSet.getString(3));
            statement.close();
        }
        return group;
    }

    @Override
    public void update(Group group) throws SQLException {
        String query = "UPDATE groups SET number = " + group.getNumber()
                + ", facultyName = '" + group.getFacultyName()
                + "'WHERE id = " + group.getId();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            statement.close();
        }
    }

    @Override
    public void delete(Group group) throws SQLException {
        String query = "DELETE FROM groups WHERE id = " + group.getId();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            statement.close();
        }
    }

    @Override
    public List<Group> getAll() throws SQLException {
        List<Group> groups = new LinkedList<>();
        String query = "SELECT id, number, facultyName FROM groups";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                groups.add(new Group(resultSet.getLong(1), resultSet.getInt(2), resultSet.getString(3)));
            }
            statement.close();
        }
        return groups;
    }

}
