package com.testtask.webservice.dao.jdbc.sqlserver;

import com.testtask.webservice.dao.DocsOptionalDAO;
import com.testtask.webservice.dao.exceptions.StorageException;
import com.testtask.webservice.model.DocsOptional;
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
public class DocsOptionalDAOSqlServerImpl extends GenericDAOSqlServerImpl<DocsOptional, Long>
        implements DocsOptionalDAO {

    @Override
    public void setPrepareStatementParametersForInsert(PreparedStatement statement, DocsOptional o) throws StorageException {
        try {
            setCommonParameters(statement, o);
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForInsert method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }


    @Override
    public void setPrepareStatementParametersForUpdate(PreparedStatement statement, DocsOptional o) throws StorageException {
        try {
            setCommonParameters(statement, o);
            statement.setLong(4, o.getId());
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForUpdate method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO docsOptional(namePart, shifr, objectId) VALUES (?,?,?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE docsOptional SET namePart=?, shifr=?, objectId=? WHERE docsOptionalId=?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM docsOptional WHERE docsOptionalId = ?;";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT docsOptionalId, namePart, shifr, objectId FROM docsOptional WHERE docsOptionalId = ?;";
    }

    @Override
    public String getSelectQueryForLastInsertedObject() {
        return "SELECT IDENT_CURRENT('docsOptional')";
    }

    @Override
    protected String getSelectQueryForAllObjects() {
        return "SELECT * FROM docsOptional;";
    }

    @Override
    public List<DocsOptional> parseResultSet(ResultSet rs, boolean isParseOnlyId) throws StorageException {
        List<DocsOptional> docses = new LinkedList<DocsOptional>();
        try {
            while (rs.next()) {
                DocsOptional docsOptional = new DocsOptional();
                docsOptional.setId(rs.getLong(1));
                if (!isParseOnlyId) {
                    docsOptional.setNamePart(rs.getString("namePart"));
                    docsOptional.setShifr(rs.getString("shifr"));
                    docsOptional.setObjectId(rs.getLong("objectId"));
                }
                docses.add(docsOptional);
            }
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in parseResultSet method " + Constants.WHILE_GET_PARAMETERS + " from ResultSet. " + e.getMessage());
        }
        return docses;
    }

    private void setCommonParameters(PreparedStatement statement, DocsOptional o) throws SQLException {
        statement.setString(1, o.getNamePart());
        statement.setString(2, o.getShifr());
        statement.setLong(3, o.getObjectId());
    }

}
