package com.testtask.webservice.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Сергей on 21.07.2016.
 */
@XmlRootElement(name = "Message", namespace = "http://testtask.org/spring/ws/api")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateTicketResponse {

    @XmlElement(name = "Status", namespace = "http://testtask.org/spring/ws/api")
    private String response;

    public CreateTicketResponse() {
    }

    public CreateTicketResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
