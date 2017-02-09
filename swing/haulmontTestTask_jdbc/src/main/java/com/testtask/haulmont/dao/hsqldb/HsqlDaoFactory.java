package com.testtask.haulmont.dao.hsqldb;

import com.testtask.haulmont.dao.DaoFactory;
import com.testtask.haulmont.dao.GroupDao;
import com.testtask.haulmont.dao.StudentDao;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Сергей
 */
public class HsqlDaoFactory implements DaoFactory {

    public HsqlDaoFactory() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("НЕ удалось загрузить драйвер ДБ.");
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:db/haulmont_test_task_db", "SA", "");
        } catch (SQLException e) {
            System.err.println("НЕ удалось создать соединение.");
            e.printStackTrace();
            System.exit(1);
        }
        return connection;
    }

    @Override
    public GroupDao getGroupDao(Connection connection) {
        return new HsqlGroupDao(connection);
    }

    @Override
    public StudentDao getStudentDao(Connection connection) {
        return new HsqlStudentDao(connection);
    }

    public void createDb(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try {
                String query = FileUtils.readFileToString(
                        new File("./sources/resources/scripts/createTables.sql"));
                statement.executeUpdate(query);
            } catch (SQLException | IOException e) {
                System.out.println("error " + e.getMessage());
            }
        }
    }

}
