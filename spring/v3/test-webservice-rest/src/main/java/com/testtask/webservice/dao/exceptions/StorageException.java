package com.testtask.webservice.dao.exceptions;

/**
 * Created by ryabov on 19.04.2016.
 */
public class StorageException extends Exception {

    public StorageException(String ex){
        super(ex);
    }

    public StorageException(Exception ex){
        super(ex);
    }
}
