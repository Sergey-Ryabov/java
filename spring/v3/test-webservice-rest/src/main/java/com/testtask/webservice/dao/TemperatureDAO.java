package com.testtask.webservice.dao;

import com.testtask.webservice.dao.exceptions.StorageException;
import com.testtask.webservice.model.Temperature;

/**
 * Created by ryabov on 19.04.2016.
 */
public interface TemperatureDAO extends GenericDAO<Temperature, Long> {

    double getAverageTemperature() throws StorageException;
}
