package com.testtask.webservice.endpoint.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by ryabov on 25.07.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AppDataResponsePart {

    @XmlElement(name="return", namespace = "http://testtask.org/spring/ws/api/l1")
    private String returnField;

    public AppDataResponsePart() {
    }

    public AppDataResponsePart(String returnField) {
        this.returnField = returnField;
    }

    public String getReturnField() {
        return returnField;
    }

    public void setReturnField(String returnField) {
        this.returnField = returnField;
    }
}
