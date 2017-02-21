package com.testtask.webservice.dao.jdbc.mysql;

import com.testtask.webservice.model.Temperature;
import com.testtask.webservice.dao.TemperatureDAO;
import com.testtask.webservice.dao.exceptions.StorageException;
import com.testtask.webservice.utils.Constants;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by ryabov on 19.04.2016.
 */
//@Repository
public class TemperatureDAOMySqlImpl extends GenericDAOMySqlImpl<Temperature, Long> implements TemperatureDAO {


    @Override
    public double getAverageTemperature() throws StorageException {
        double averageTemperture = Double.MIN_VALUE;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String sql = getSelectQueryForAverageTemperature();
            PreparedStatement statement = connection.prepareStatement(sql);

            Timestamp now = new Timestamp(new Date().getTime());
            Timestamp hourAgo = new Timestamp(now.getTime() - 3600000);
            Calendar calendar = Calendar.getInstance();
            statement.setTimestamp(1, hourAgo, calendar);
            statement.setTimestamp(2, now, calendar);
            ResultSet rs = statement.executeQuery();
            averageTemperture = parseResultSetForAverageTemperature(rs, false);
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    return averageTemperture;
                } catch (Exception e) {
                    throw new StorageException(e);
                }
            }
            return averageTemperture;
        }
    }

    @Override
    protected void setPrepareStatementParametersForInsert(PreparedStatement statement, Temperature o) throws StorageException {
        try {
            setCommonParameters(statement, o);
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForInsert method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }

    @Override
    protected void setPrepareStatementParametersForUpdate(PreparedStatement statement, Temperature o) throws StorageException {
        try {
            setCommonParameters(statement, o);
            statement.setLong(3, o.getId());
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForUpdate method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO temperature(tempValue, tempDate) VALUES (?,?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE temperature SET tempValue=?, tempDate=? WHERE temperatureId=?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM temperature WHERE temperatureId = ?;";
    }

    protected String getSelectQueryForAverageTemperature() {
        return "SELECT AVG(tempValue) FROM temperature WHERE tempDate BETWEEN ? AND ?";
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT temperatureId, tempValue, tempDate FROM temperature WHERE temperatureId = ?;";
    }

    @Override
    protected String getSelectQueryForLastInsertedObject() {
        return "SELECT MAX(temperatureId) FROM temperature;";
    }

    @Override
    protected String getSelectQueryForAllObjects() {
        return "SELECT * FROM temperature;";
    }

    @Override
    protected List<Temperature> parseResultSet(ResultSet rs, boolean isParseOnlyId) throws StorageException {
        List<Temperature> temperatures = new LinkedList<Temperature>();
        try {
            while (rs.next()) {
                Temperature temperature = new Temperature();
                temperature.setId(rs.getLong(1));
                if (!isParseOnlyId) {
                    temperature.setTempValue(rs.getDouble("tempValue"));
                    Timestamp tempDate = rs.getTimestamp("tempDate");
                    if (tempDate != null) {
                        temperature.setTempDate(tempDate);
                    } else {
                        temperature.setTempDate(new Timestamp(new java.util.Date().getTime()));
                    }
                }
                temperatures.add(temperature);
            }
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in parseResultSet method " + Constants.WHILE_GET_PARAMETERS + " from ResultSet. " + e.getMessage());
        }
        return temperatures;
    }

    protected double parseResultSetForAverageTemperature(ResultSet rs, boolean isParseOnlyId) throws StorageException {
        try {
            while (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in parseResultSet method " + Constants.WHILE_GET_PARAMETERS + " from ResultSet. " + e.getMessage());
        }
        return Double.MIN_VALUE;
    }


    private void setCommonParameters(PreparedStatement statement, Temperature o) throws SQLException {
        statement.setDouble(1, o.getTempValue());
        statement.setTimestamp(2, new Timestamp(o.getTempDate().getTime()));
    }

}
