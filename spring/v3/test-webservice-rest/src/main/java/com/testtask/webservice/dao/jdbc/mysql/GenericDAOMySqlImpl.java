package com.testtask.webservice.dao.jdbc.mysql;

import com.testtask.webservice.dao.GenericDAO;
import com.testtask.webservice.dao.exceptions.StorageException;
import com.testtask.webservice.model.Identifier;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


/**
 * Created by ryabov on 19.04.2016.
 */
//@Repository
public abstract class GenericDAOMySqlImpl<T extends Identifier, ID extends Serializable> implements GenericDAO<T, ID> {


    //    @Autowired
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ID create(T o) throws StorageException {
        if (o.getId() != null && o.getId() != 0) {
            throw new StorageException("Object " + o.getClass() + " is already persist.");
        }
        T persistInstance;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String sql = getInsertQuery();
            PreparedStatement statementForInsert = connection.prepareStatement(sql);
            setPrepareStatementParametersForInsert(statementForInsert, o);
            int count = statementForInsert.executeUpdate();
            if (count != 1) {
                throw new StorageException("On persist modify more then 1 record: " + count);
            }
            sql = getSelectQueryForLastInsertedObject();
            PreparedStatement statementForGetId = connection.prepareStatement(sql);
            ResultSet rs = statementForGetId.executeQuery();
            List<T> list = parseResultSet(rs, true);
            if ((list == null) || (list.size() != 1)) {
                throw new StorageException("Exception while get ID for persisted object.");
            }
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new StorageException(e);
                }
            }
        }
        return (ID) persistInstance.getId();
    }

    public T read(ID id) throws StorageException {
        List<T> list = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String sql = getSelectQuery();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs, false);
            statement.close();
            if (list == null || list.size() == 0) {
                return null;
            }
            if (list.size() > 1) {
                throw new StorageException("Received more than 1 record.");
            }
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new StorageException(e);
                }
            }
        }
        return list.iterator().next();
    }

    public void update(T o) throws StorageException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String sql = getUpdateQuery();
            PreparedStatement statement = connection.prepareStatement(sql);
            setPrepareStatementParametersForUpdate(statement, o);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new StorageException("On update modify more then 1 record: " + count);
            }
            statement.close();
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new StorageException(e);
                }
            }
        }
    }

    public void delete(T o) throws StorageException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String sql = getDeleteQuery();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, o.getId());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new StorageException("On delete modify more then 1 record: " + count);
            }
            statement.close();
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new StorageException(e);
                }
            }
        }
    }

    public void deleteById(ID id) throws StorageException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String sql = getDeleteQuery();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, (Long) id);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new StorageException("On delete modify more then 1 record: " + count);
            }
            statement.close();
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new StorageException(e);
                }
            }
        }
    }

    @Override
    public List<T> getAll() throws StorageException {
        Connection connection = null;
        List<T> objects = null;
        try {
            connection = dataSource.getConnection();
            String sql = getSelectQueryForAllObjects();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            objects = parseResultSet(rs, false);
            statement.close();
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new StorageException(e);
                }
            }
        }
        return objects;
    }

    protected abstract void setPrepareStatementParametersForInsert(PreparedStatement statement, T o) throws StorageException;

    protected abstract void setPrepareStatementParametersForUpdate(PreparedStatement statement, T o) throws StorageException;

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getSelectQuery();

    protected abstract String getSelectQueryForLastInsertedObject();

    protected abstract String getSelectQueryForAllObjects();

    protected abstract List<T> parseResultSet(ResultSet rs, boolean isParseOnlyId) throws StorageException;

}
