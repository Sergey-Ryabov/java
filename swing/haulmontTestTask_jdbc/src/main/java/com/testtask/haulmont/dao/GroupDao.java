package com.testtask.haulmont.dao;

import com.testtask.haulmont.model.Group;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Сергей
 */
public interface GroupDao {

    Group createGroup(int number, String facultyName) throws SQLException;

    Group read(long id) throws SQLException;

    void update(Group group) throws SQLException;

    void delete(Group group) throws SQLException;

    List<Group> getAll() throws SQLException;

}
