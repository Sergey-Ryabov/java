package com.testtask.webservice.model;

import java.io.Serializable;

/**
 * Created by ryabov on 19.04.2016.
 */
public class DocsOptional implements Serializable, Identifier{

    private long id;
    private String namePart;
    private String shifr;
    private long objectId;

    public DocsOptional() {
    }

    public DocsOptional(long id, String namePart, String shifr, long objectId) {
        this.id = id;
        this.namePart = namePart;
        this.shifr = shifr;
        this.objectId = objectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    public String getShifr() {
        return shifr;
    }

    public void setShifr(String shifr) {
        this.shifr = shifr;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "DocsOptional{" +
                "id=" + id +
                ", namePart='" + namePart + '\'' +
                ", shifr='" + shifr + '\'' +
                ", objectId='" + objectId + '\'' +
                '}';
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof DocsOptional)) return false;

        DocsOptional docsOptional = (DocsOptional) o;

        return id == docsOptional.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
