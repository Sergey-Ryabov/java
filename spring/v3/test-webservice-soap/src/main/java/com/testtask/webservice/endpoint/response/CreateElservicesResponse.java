package com.testtask.webservice.endpoint.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ryabov on 25.07.2016.
 */
@XmlRootElement(name = "createElservicesResponse", namespace = "http://testtask.org/spring/ws/api/l1")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateElservicesResponse {

    private String exception;

    @XmlElement(name = "MessageData", namespace = "http://testtask.org/spring/ws/api/l2")
    private MessageDataResponsePart messageData;
    @XmlElement(name = "Message", namespace = "http://testtask.org/spring/ws/api/l2")
    private MessageResponsePart message;

    public CreateElservicesResponse() {
    }

    public CreateElservicesResponse(MessageDataResponsePart messageData, MessageResponsePart message) {
        this.messageData = messageData;
        this.message = message;
    }

    public CreateElservicesResponse(String exception) {
        this.exception = exception;
    }

    public MessageDataResponsePart getMessageData() {
        return messageData;
    }

    public void setMessageData(MessageDataResponsePart messageData) {
        this.messageData = messageData;
    }

    public MessageResponsePart getMessage() {
        return message;
    }

    public void setMessage(MessageResponsePart message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
