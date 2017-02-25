package com.testtask.webservice.endpoint.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by ryabov on 25.07.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageResponsePart {

    @XmlElement(name = "Sender", namespace = "http://testtask.org/spring/ws/api/l2")
    private Role sender;
    @XmlElement(name = "Recipient", namespace = "http://testtask.org/spring/ws/api/l2")
    private Role recipient;
    @XmlElement(name = "Originator", namespace = "http://testtask.org/spring/ws/api/l2")
    private Role originator;
    @XmlElement(name = "TypeCode", namespace = "http://testtask.org/spring/ws/api/l2")
    private String typeCode;
    @XmlElement(name = "Status", namespace = "http://testtask.org/spring/ws/api/l2")
    private String status;
    @XmlElement(name = "Date", namespace = "http://testtask.org/spring/ws/api/l2")
    private String date;
    @XmlElement(name = "ExchangeType", namespace = "http://testtask.org/spring/ws/api/l2")
    private String exchangeType;
    @XmlElement(name = "RequestIdRef", namespace = "http://testtask.org/spring/ws/api/l2")
    private String requestIdRef;
    @XmlElement(name = "OriginRequestIdRef", namespace = "http://testtask.org/spring/ws/api/l2")
    private String originRequestIdRef;
    @XmlElement(name = "ServiceCode", namespace = "http://testtask.org/spring/ws/api/l2")
    private String serviceCode;
    @XmlElement(name = "CaseNumber", namespace = "http://testtask.org/spring/ws/api/l2")
    private String caseNumber;

    public MessageResponsePart() {
    }

    public MessageResponsePart(Role sender, Role recipient, Role originator, String typeCode, String status, String date, String exchangeType, String requestIdRef, String originRequestIdRef, String serviceCode, String caseNumber) {
        this.sender = sender;
        this.recipient = recipient;
        this.originator = originator;
        this.typeCode = typeCode;
        this.status = status;
        this.date = date;
        this.exchangeType = exchangeType;
        this.requestIdRef = requestIdRef;
        this.originRequestIdRef = originRequestIdRef;
        this.serviceCode = serviceCode;
        this.caseNumber = caseNumber;
    }

    public Role getSender() {
        return sender;
    }

    public void setSender(Role sender) {
        this.sender = sender;
    }

    public Role getRecipient() {
        return recipient;
    }

    public void setRecipient(Role recipient) {
        this.recipient = recipient;
    }

    public Role getOriginator() {
        return originator;
    }

    public void setOriginator(Role originator) {
        this.originator = originator;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getRequestIdRef() {
        return requestIdRef;
    }

    public void setRequestIdRef(String requestIdRef) {
        this.requestIdRef = requestIdRef;
    }

    public String getOriginRequestIdRef() {
        return originRequestIdRef;
    }

    public void setOriginRequestIdRef(String originRequestIdRef) {
        this.originRequestIdRef = originRequestIdRef;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }
}
