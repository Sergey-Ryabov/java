package com.testtask.webservice.controller;


import com.testtask.webservice.dao.TemperatureDAO;
import com.testtask.webservice.dao.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Created by ryabov on 26.04.2016.
 */

@Controller
@RequestMapping(value = "/temperatures")
public class TemperatureController {

    private static Logger logger = Logger.getLogger(TemperatureController.class.getName());

    @Autowired
    TemperatureDAO temperatureDAO;

    @RequestMapping(value = "average", method = RequestMethod.GET)
    public @ResponseBody String getAverageTemperature() {
        double averageTemp;
        try {
            averageTemp = temperatureDAO.getAverageTemperature();
        } catch (StorageException e) {
            logger.warning(e.getMessage());
            return "{\"errorCode\": 500, \"errorMessage\": \"" + e.getMessage() + "\"}";
        }
        return "{\"averageTemp\": " + averageTemp + '}';
    }

}
