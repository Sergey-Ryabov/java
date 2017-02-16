package org.javafx.ais.introductionofchanges.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "POSITION")
public class Position implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "POSITION_ID")
    private long id;

    @Column(name = "POSITION_NAME", unique = true)
    private String name;

    public Position() {
    }

    public Position(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
//        return "Position{" + "id=" + id + ", name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
