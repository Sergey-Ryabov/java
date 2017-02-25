package com.testtask.webservice.dao.jdbc.sqlserver;

import com.testtask.webservice.dao.ObjectDAO;
import com.testtask.webservice.dao.exceptions.StorageException;
import com.testtask.webservice.model.Object;
import com.testtask.webservice.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ryabov on 19.04.2016.
 */
//@Repository
public class ObjectDAOSqlServerImpl extends GenericDAOSqlServerImpl<com.testtask.webservice.model.Object, Long>
        implements ObjectDAO {

    @Override
    public void setPrepareStatementParametersForInsert(PreparedStatement statement, Object o) throws StorageException {
        try {
            setCommonParameters(statement, o);
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForInsert method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }


    @Override
    public void setPrepareStatementParametersForUpdate(PreparedStatement statement, Object o) throws StorageException {
        try {
            setCommonParameters(statement, o);
            statement.setLong(5, o.getId());
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForUpdate method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO objects(objectTypeId, nameObject, power, applicantId) " +
                "VALUES (?,?,?,?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE objects SET objectTypeId=?, nameObject=?, power=?, applicantId=? WHERE objectId=?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM objects WHERE objectId = ?;";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT objectId, objectTypeId, nameObject, power,  applicantId FROM objects WHERE objectId = ?;";
    }

    @Override
    public String getSelectQueryForLastInsertedObject() {
        return "SELECT IDENT_CURRENT('objects')";
    }

    @Override
    protected String getSelectQueryForAllObjects() {
        return "SELECT * FROM objects;";
    }

    @Override
    public List<Object> parseResultSet(ResultSet rs, boolean isParseOnlyId) throws StorageException {
        List<Object> objects = new LinkedList<Object>();
        try {
            while (rs.next()) {
                Object object = new Object();
                object.setId(rs.getLong(1));
                if (!isParseOnlyId) {
                    object.setObjectTypeId(rs.getLong("objectTypeId"));
                    object.setNameObject(rs.getString("nameObject"));
                    object.setPower(rs.getInt("power"));
                    object.setApplicantId(rs.getLong("applicantId"));
                }
                objects.add(object);
            }
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in parseResultSet method " + Constants.WHILE_GET_PARAMETERS + " from ResultSet. " + e.getMessage());
        }
        return objects;
    }


    private void setCommonParameters(PreparedStatement statement, Object o) throws SQLException {
        statement.setLong(1, o.getObjectTypeId());
        statement.setString(2, o.getNameObject());
        statement.setInt(3, o.getPower());
        statement.setLong(4, o.getApplicantId());
    }

}
