package com.testtask.webservice.dao.jdbc.sqlserver;

import com.testtask.webservice.dao.DocsDAO;
import com.testtask.webservice.dao.exceptions.StorageException;
import com.testtask.webservice.model.Docs;
import com.testtask.webservice.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ryabov on 14.07.2016.
 */
public class DocsDAOSqlServerImpl  extends GenericDAOSqlServerImpl<Docs, Long>
        implements DocsDAO {

    @Override
    public void setPrepareStatementParametersForInsert(PreparedStatement statement, Docs o) throws StorageException {
        try {
            setCommonParameters(statement, o);
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForInsert method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }


    @Override
    public void setPrepareStatementParametersForUpdate(PreparedStatement statement, Docs o) throws StorageException {
        try {
            setCommonParameters(statement, o);
            statement.setLong(3, o.getId());
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForUpdate method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO docs(requiredInfo, objectId) VALUES (?,?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE docs SET requiredInfo=?, objectId=? WHERE docsId=?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM docs WHERE docsId = ?;";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT docsId, requiredInfo, objectId FROM docs WHERE docsId = ?;";
    }

    @Override
    public String getSelectQueryForLastInsertedObject() {
        return "SELECT IDENT_CURRENT('docs')";
    }

    @Override
    protected String getSelectQueryForAllObjects() {
        return "SELECT * FROM docs;";
    }

    @Override
    public List<Docs> parseResultSet(ResultSet rs, boolean isParseOnlyId) throws StorageException {
        List<Docs> docses = new LinkedList<Docs>();
        try {
            while (rs.next()) {
                Docs docs = new Docs();
                docs.setId(rs.getLong(1));
                if (!isParseOnlyId) {
                    docs.setRequiredInfo(rs.getString("requiredInfo"));
                    docs.setObjectId(rs.getLong("objectId"));
                }
                docses.add(docs);
            }
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in parseResultSet method " + Constants.WHILE_GET_PARAMETERS + " from ResultSet. " + e.getMessage());
        }
        return docses;
    }

    private void setCommonParameters(PreparedStatement statement, Docs o) throws SQLException {
        statement.setString(1, o.getRequiredInfo());
        statement.setLong(2, o.getObjectId());
    }
}
