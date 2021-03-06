package com.testtask.webservice.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * Created by ryabov on 19.04.2016.
 */
@XmlRootElement(name = "Temperature")
public class Temperature implements Serializable, Identifier {

    private Long id;
    private double tempValue;
    private Date tempDate;


    public Temperature() {
        tempDate = new Date();
    }

    public Temperature(double tempValue, Date tempDate) {
        this.tempValue = tempValue;
        if (tempDate != null) {
            this.tempDate = tempDate;
        } else {
            this.tempDate = new Date();
        }
    }

    public Temperature(Long id, double tempValue, Date tempDate) {
        this.id = id;
        this.tempValue = tempValue;
        if (tempDate != null) {
            this.tempDate = tempDate;
        } else {
            this.tempDate = new Date();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTempValue() {
        return tempValue;
    }

    public void setTempValue(double tempValue) {
        this.tempValue = tempValue;
    }

    public Date getTempDate() {
        return tempDate;
    }

    public void setTempDate(Date tempDate) {
        this.tempDate = tempDate;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "id=" + id +
                ", tempValue=" + tempValue +
                ", tempDate=" + tempDate +
                '}';
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Temperature)) return false;

        Temperature temperature = (Temperature) o;

        return id == temperature.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
