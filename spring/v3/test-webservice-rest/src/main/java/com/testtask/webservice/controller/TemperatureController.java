package com.testtask.webservice.controller;


import com.testtask.webservice.dao.TemperatureDAO;
import com.testtask.webservice.dao.exceptions.StorageException;
import com.testtask.webservice.model.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Created by ryabov on 26.04.2016.
 */

@Controller
@RequestMapping(value = "/temperatures",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class TemperatureController {

    private static Logger logger = Logger.getLogger(TemperatureController.class.getName());

    @Autowired
    TemperatureDAO temperatureDAO;

    @RequestMapping(value = "average", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getAverageTemperature() {
        double averageTemp;
        try {
            averageTemp = temperatureDAO.getAverageTemperature();
        } catch (StorageException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity("{\"errorMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("{\"averageTemp\": " + averageTemp + "}", HttpStatus.OK);
    }

    @RequestMapping(value = "/{temperatureId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getAverageTemperature(@PathVariable long temperatureId) {
        try {
            Temperature temperature = temperatureDAO.read(temperatureId);
            if (temperature == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(temperature, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (StorageException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity("{\"errorMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createTemperature(@RequestBody Temperature temperature) {
        try {
            Long temperatureId = temperatureDAO.create(temperature);
            if (temperatureId == null) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                temperature.setId(temperatureId);
                return new ResponseEntity(temperature, HttpStatus.CREATED);
            }
        } catch (StorageException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity("{\"errorMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{temperatureId}", method = RequestMethod.PUT)
    public ResponseEntity updateTemperature(@PathVariable long temperatureId, @RequestBody Temperature temperature) {
        try {
            Temperature temperatureFromDB = temperatureDAO.read(temperatureId);
            if (temperatureFromDB == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                temperatureDAO.update(temperature);
                return new ResponseEntity(temperature, HttpStatus.OK);
            }
        } catch (StorageException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity("{\"errorMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{temperatureId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemperature(@PathVariable long temperatureId) {
        try {
            Temperature temperatureFromDB = temperatureDAO.read(temperatureId);
            if (temperatureFromDB == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                temperatureDAO.delete(temperatureFromDB);
                return new ResponseEntity(HttpStatus.OK);
            }
        } catch (StorageException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity("{\"errorMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
