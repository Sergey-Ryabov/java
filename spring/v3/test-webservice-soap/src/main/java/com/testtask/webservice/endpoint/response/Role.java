package com.testtask.webservice.endpoint.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by ryabov on 25.07.2016.
 */
//Sender || Recipient || Originator
@XmlAccessorType(XmlAccessType.FIELD)
public class Role {

    @XmlElement(name = "Code", namespace = "http://testtask.org/spring/ws/api/l2")
    private String code;
    @XmlElement(name = "Name", namespace = "http://testtask.org/spring/ws/api/l2")
    private String name;

    public Role() {
    }

    public Role(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
