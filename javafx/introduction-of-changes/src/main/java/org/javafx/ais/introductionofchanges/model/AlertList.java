package org.javafx.ais.introductionofchanges.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Сергей
 */
@Entity
@Table(name = "ALERT_LIST")
public class AlertList implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ALERT_LIST_ID")
    private long id;

    @Column(name = "ALERT_LIST_NAME")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "alertListsInEmployee")
    private Set<Employee> employees;

    public AlertList() {
        employees = new HashSet<>();
    }

    public AlertList(String name) {
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        if (id == 0) {
            return "Списки оповещения";
        } else {
            return name;
//            return "AlertList{" + "id=" + id + ", name=" + name + '}';
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final AlertList other = (AlertList) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
