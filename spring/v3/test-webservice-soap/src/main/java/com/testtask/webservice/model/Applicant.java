package com.testtask.webservice.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


/**
 * Created by ryabov on 19.04.2016.
 */
@XmlRootElement(name = "Applicant")
public class Applicant implements Serializable, Identifier {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String personINN;
    private String personEMail;

    private List<Object> objects;


    public Applicant() {

    }

    public Applicant(Long id, String lastName, String firstName, String middleName, String personINN, String personEMail) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.personINN = personINN;
        this.personEMail = personEMail;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }


    public String getPersonINN() {
        return personINN;
    }

    public void setPersonINN(String personINN) {
        this.personINN = personINN;
    }

    public String getPersonEMail() {
        return personEMail;
    }

    public void setPersonEMail(String personEMail) {
        this.personEMail = personEMail;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", personINN='" + personINN + '\'' +
                ", personEMail='" + personEMail + '\'' +
                '}';
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Applicant)) return false;

        Applicant applicant = (Applicant) o;

        return id == applicant.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
