package com.testtask.haulmont.dao;

import java.sql.Connection;

/**
 *
 * @author Сергей
 */
public interface DaoFactory {

    Connection getConnection();

    GroupDao getGroupDao(Connection connection);

    StudentDao getStudentDao(Connection connection);

}
