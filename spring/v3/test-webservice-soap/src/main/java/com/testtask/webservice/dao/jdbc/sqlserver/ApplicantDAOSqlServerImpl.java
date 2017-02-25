package com.testtask.webservice.dao.jdbc.sqlserver;

import com.testtask.webservice.dao.ApplicantDAO;
import com.testtask.webservice.dao.exceptions.StorageException;
import com.testtask.webservice.model.Applicant;
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
public class ApplicantDAOSqlServerImpl extends GenericDAOSqlServerImpl<Applicant, Long> implements ApplicantDAO {

    @Override
    protected void setPrepareStatementParametersForInsert(PreparedStatement statement, Applicant o) throws StorageException {
        try {
            setCommonParameters(statement, o);
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForInsert method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }

    @Override
    protected void setPrepareStatementParametersForUpdate(PreparedStatement statement, Applicant o) throws StorageException {
        try {
            setCommonParameters(statement, o);
            statement.setLong(6, o.getId());
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in setPrepareStatementParametersForUpdate method " + Constants.WHILE_SET_PARAMETERS + e.getMessage());
        }
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO applicant(lastName, firstName, middleName, personINN, personEMail) " +
                "VALUES (?,?,?,?,?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE applicant SET lastName=?, firstName=?, middleName=?, personINN=?, personEMail=? WHERE applicantId=?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM applicant WHERE applicantID = ?;";
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT applicantId, lastName, firstName, middleName, personINN, personEMail FROM applicant WHERE applicantId = ?;";
    }

    @Override
    protected String getSelectQueryForLastInsertedObject() {
        return "SELECT IDENT_CURRENT('applicant');";
    }

    @Override
    protected String getSelectQueryForAllObjects() {
        return "SELECT * FROM applicant;";
    }

    @Override
    protected List<Applicant> parseResultSet(ResultSet rs, boolean isParseOnlyId) throws StorageException {
        List<Applicant> applicants = new LinkedList<Applicant>();
        try {
            while (rs.next()) {
                Applicant applicant = new Applicant();
                applicant.setId(rs.getLong(1));
                if (!isParseOnlyId) {
                    applicant.setLastName(rs.getString("lastName"));
                    applicant.setFirstName(rs.getString("firstName"));
                    applicant.setMiddleName(rs.getString("middleName"));
                    applicant.setPersonINN(rs.getString("personINN"));
                    applicant.setPersonEMail(rs.getString("personEMail"));
                }
                applicants.add(applicant);
            }
        } catch (Exception e) {
            throw new StorageException(Constants.EXCEPTION_IN + this.getClass() + " in parseResultSet method " + Constants.WHILE_GET_PARAMETERS + " from ResultSet. " + e.getMessage());
        }
        return applicants;
    }


    private void setCommonParameters(PreparedStatement statement, Applicant o) throws SQLException {
        statement.setString(1, o.getLastName());
        statement.setString(2, o.getFirstName());
        statement.setString(3, o.getMiddleName());
        statement.setString(4, o.getPersonINN());
        statement.setString(5, o.getPersonEMail());
    }

}
