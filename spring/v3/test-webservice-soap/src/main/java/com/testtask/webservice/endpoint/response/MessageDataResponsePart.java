package com.testtask.webservice.endpoint.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by ryabov on 25.07.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageDataResponsePart {

    @XmlElement(name = "AppData", namespace = "http://testtask.org/spring/ws/api/l2")
    private AppDataResponsePart appDataResponsePart;

    public MessageDataResponsePart() {
    }

    public MessageDataResponsePart(AppDataResponsePart appDataResponsePart) {
        this.appDataResponsePart = appDataResponsePart;
    }

    public AppDataResponsePart getAppDataResponsePart() {
        return appDataResponsePart;
    }

    public void setAppDataResponsePart(AppDataResponsePart appDataResponsePart) {
        this.appDataResponsePart = appDataResponsePart;
    }
}
