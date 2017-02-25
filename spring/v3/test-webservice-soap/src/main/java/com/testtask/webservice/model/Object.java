package com.testtask.webservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ryabov on 19.04.2016.
 */
public class Object implements Serializable, Identifier {

    private long id;
    private long objectTypeId;

    private String nameObject;
    private int power;
    private long applicantId;

    private List<DocsOptional> docsOptionals;
    private Docs docs;

    public Object() {
    }


    public Object(long id, long objectTypeId,  String nameObject, int power, long applicantId) {
        this.id = id;
        this.objectTypeId = objectTypeId;
        this.nameObject = nameObject;
        this.power = power;
        this.applicantId = applicantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getObjectTypeId() {
        return objectTypeId;
    }

    public void setObjectTypeId(long objectTypeId) {
        this.objectTypeId = objectTypeId;
    }


    public String getNameObject() {
        return nameObject;
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(long applicantId) {
        this.applicantId = applicantId;
    }

    public List<DocsOptional> getDocsOptionals() {
        return docsOptionals;
    }

    public void setDocsOptionals(List<DocsOptional> docsOptionals) {
        this.docsOptionals = docsOptionals;
    }

    public Docs getDocs() {
        return docs;
    }

    public void setDocs(Docs docs) {
        this.docs = docs;
    }

    @Override
    public String toString() {
        return "Object{" +
                "id=" + id +
                ", objectTypeId=" + objectTypeId +
                ", nameObject='" + nameObject + '\'' +
                ", power=" + power +
                ", applicantId=" + applicantId +
                '}';
    }

    @Override
         public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Object)) return false;

        Object object = (Object) o;

        return id == object.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
