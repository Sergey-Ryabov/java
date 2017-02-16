package org.javafx.ais.introductionofchanges.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Сергей
 */
@Entity
@Table(name = "CHANGE_ITEM_IN_CHANGES_IN_ALERT_LISTS")
public class ChangeItemInChangesInAlertLists implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "CHANGE_ITEM_IN_CHANGES_IN_ALERT_LISTS_ID")
    private long id;

    @OneToOne
    @JoinColumn(name = "CHANGES_IN_ALERT_LISTS")
    private ChangesInAlertLists changesInAlertLists;

    @Column(name = "CHANGE_DATE")
    private LocalDateTime changeDate;

    @OneToOne
    @JoinColumn(name = "EMPLOYEE_WHO_MADE_CHANGE")
    private Employee employeeWhoMadeChange;

    @Lob
    @Column(name = "CHANGES_IN_CHANGES_IN_ALERT_LISTS", length = 4000)
    private String changesInChangesInAlertLists;

    public ChangeItemInChangesInAlertLists() {
    }

    public ChangeItemInChangesInAlertLists(ChangesInAlertLists changesInAlertLists, LocalDateTime changeDate,
                                           Employee employeeWhoMadeChange, String changesInChangesInAlertLists) {
        this.changesInAlertLists = changesInAlertLists;
        this.changeDate = changeDate;
        this.employeeWhoMadeChange = employeeWhoMadeChange;
        this.changesInChangesInAlertLists = changesInChangesInAlertLists;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChangesInAlertLists getChangesInAlertLists() {
        return changesInAlertLists;
    }

    public void setChangesInAlertLists(ChangesInAlertLists changesInAlertLists) {
        this.changesInAlertLists = changesInAlertLists;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }

    public Employee getEmployeeWhoMadeChange() {
        return employeeWhoMadeChange;
    }

    public void setEmployeeWhoMadeChange(Employee employeeWhoMadeChange) {
        this.employeeWhoMadeChange = employeeWhoMadeChange;
    }

    public String getChangesInChangesInAlertLists() {
        return changesInChangesInAlertLists;
    }

    public void setChangesInChangesInAlertLists(String changesInChangesInAlertLists) {
        this.changesInChangesInAlertLists = changesInChangesInAlertLists;
    }

    @Override
    public String toString() {
        return "ChangeItemInChangesInAlertLists{" + "id=" + id + ", changesInAlertLists=" + changesInAlertLists
                + ", changeDate=" + changeDate + ", employeeWhoMadeChange=" + employeeWhoMadeChange
                + ", changes=" + changesInChangesInAlertLists + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final ChangeItemInChangesInAlertLists other = (ChangeItemInChangesInAlertLists) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }


}
