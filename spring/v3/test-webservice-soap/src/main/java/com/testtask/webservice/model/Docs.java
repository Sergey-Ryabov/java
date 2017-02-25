package com.testtask.webservice.model;

import java.io.Serializable;

/**
 * Created by ryabov on 12.07.2016.
 */
public class Docs implements Serializable, Identifier {

    private long id;
    private String requiredInfo;
    private long objectId;

    public Docs() {
    }

    public Docs(long id, String nameOrgSurveyor, long objectId) {
        this.id = id;
        this.requiredInfo = nameOrgSurveyor;
        this.objectId = objectId;
    }


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequiredInfo() {
        return requiredInfo;
    }

    public void setRequiredInfo(String requiredInfo) {
        this.requiredInfo = requiredInfo;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "Docs{" +
                "id=" + id +
                ", requiredInfo='" + requiredInfo + '\'' +
                ", objectId=" + objectId +
                '}';
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Docs)) return false;

        Docs docs = (Docs) o;

        return id == docs.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
