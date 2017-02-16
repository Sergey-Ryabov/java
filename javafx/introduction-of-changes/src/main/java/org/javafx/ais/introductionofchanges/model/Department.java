package org.javafx.ais.introductionofchanges.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "DEPARTMENT")
public class Department implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "DEPARTMENT_ID")
    private Long id;

    @Column(name = "DEPARTMENT_NAME")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "department")
    private Set<Division> divisions;

    public Department() {
    }

    public Department(String name, Set<Division> divisions) {
        this.name = name;
        this.divisions = divisions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(Set<Division> divisions) {
        this.divisions = divisions;
    }

    @Override
    public String toString() {
        if (id == 0) {
            return "Департаменты";
        } else {
            return name;
//            return "Department{" + "id=" + id + ", name=" + name + '}';
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Department other = (Department) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
