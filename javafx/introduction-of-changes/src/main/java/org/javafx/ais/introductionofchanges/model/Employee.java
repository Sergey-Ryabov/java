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
@Table(name = "EMPLOYEE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "EMPLOYEE_ID")
    private long id;
    @Column(name = "EMPLOYEE_NAME")
    private String name;
    @Column(name = "EMPLOYEE_SURNAME")
    private String surname;
    @Column(name = "EMPLOYEE_PATRONIMIC")
    private String patronimic;
    @Column(name = "EMPLOYEE_PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "EMPLOYEE_CELL_PHONE_NUMBER")
    private String cellPhoneNumber;
    @Column(name = "EMPLOYEE_IS_ON_VACATION")
    private boolean isOnVacation;
    @OneToOne
    @JoinColumn(name = "POSITION", nullable = true)
    private Position position;
    @ManyToOne
    @JoinColumn(name = "DIVISION_ID", nullable = true)
    private Division division;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "EMPLOYEE_ALERT_LIST",
            joinColumns = {
                @JoinColumn(name = "EMPLOYEE_ID", nullable = true)},
            inverseJoinColumns = {
                @JoinColumn(name = "ALERT_LIST_ID", nullable = true)})
    private Set<AlertList> alertListsInEmployee = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "CHANGES_IN_ALERT_LISTS", nullable = true)
    private ChangesInAlertLists changesInAlertLists;

    public Employee() {
    }

    public Employee(String name, String surname, String patronimic,
            String phoneNumber, String cellPhoneNumber, Position position,
            Division division) {
        this.name = name;
        this.surname = surname;
        this.patronimic = patronimic;
        this.phoneNumber = phoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;
        this.position = position;
        this.division = division;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronimic() {
        return patronimic;
    }

    public void setPatronimic(String patronimic) {
        this.patronimic = patronimic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public boolean isOnVacation() {
        return isOnVacation;
    }

    public void setIsOnVacation(boolean isOnVacation) {
        this.isOnVacation = isOnVacation;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Set<AlertList> getAlertListsInEmployee() {
        return alertListsInEmployee;
    }

    public void setAlertListsInEmployee(Set<AlertList> alertListsInEmployee) {
        this.alertListsInEmployee = alertListsInEmployee;
    }

    public ChangesInAlertLists getChangesInAlertLists() {
        return changesInAlertLists;
    }

    public void setChangesInAlertLists(ChangesInAlertLists changesInAlertLists) {
        this.changesInAlertLists = changesInAlertLists;
    }

    @Override
    public String toString() {
        if (id == 0 && position == null) {
            return "Сотрудники";
        } else {
            return  surname + " " + name + " " + patronimic;
//            return "Employee{" + "id=" + id + ", name=" + name
//                    + ", surname=" + surname
//                    + ", patronimic=" + patronimic
//                    + ((StringUtils.isNotBlank(phoneNumber)) ? ", phoneNumber=" + phoneNumber : "")
//                    + ((StringUtils.isNotBlank(cellPhoneNumber)) ? ", cellPhoneNumber=" + patronimic : "")
//                    + ", position=" + position;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Employee other = (Employee) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
