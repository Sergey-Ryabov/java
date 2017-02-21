package com.testtask.webservice.dao;

import com.testtask.webservice.dao.exceptions.StorageException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ryabov on 19.04.2016.
 */
public interface GenericDAO<T, ID extends Serializable> {

    ID create(T newInstance)throws StorageException;

    T read(ID id)throws StorageException;

    void update(T transientObject) throws StorageException;

    void delete(T persistentObject) throws StorageException;

    void deleteById(ID id) throws StorageException;

    List<T> getAll() throws StorageException;

}
