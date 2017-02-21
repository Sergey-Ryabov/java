package com.testtask.webservice;

/**
 * Created by ryabov on 15.07.2016.
 */

import com.testtask.webservice.dao.TemperatureDAO;
import com.testtask.webservice.model.Temperature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext ctx = null;

    private final static Random random = new Random();

    @Autowired
    TemperatureDAO temperatureDAO;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        Thread.sleep(10000);
                        if(temperatureDAO != null){
                            temperatureDAO.create(new Temperature(random.nextDouble() + random.nextInt(101), new Timestamp(new Date().getTime())));
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public TemperatureDAO getTemperatureDAO() {
        return temperatureDAO;
    }

    public void setTemperatureDAO(TemperatureDAO temperatureDAO) {
        this.temperatureDAO = temperatureDAO;
    }
}