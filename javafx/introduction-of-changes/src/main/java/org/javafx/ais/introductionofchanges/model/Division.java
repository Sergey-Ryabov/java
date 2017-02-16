package org.javafx.ais.introductionofchanges.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "DIVISION")
public class Division implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "DIVISION_ID")
    private long id;

    @Column(name = "DIVISION_NAME")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "division")
    private Set<Employee> employees;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", nullable = true)
    private Department department;

    public Division() {
        employees = new HashSet<Employee>();
    }

    public Division(String name, Set<Employee> employees, Department department) {
        this.name = name;
        this.employees = employees;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        if (id == 0) {
            return "Отделы";
        } else {
            return name;
//            return "Division{" + "id=" + id + ", name=" + name + '}';
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Division other = (Division) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
