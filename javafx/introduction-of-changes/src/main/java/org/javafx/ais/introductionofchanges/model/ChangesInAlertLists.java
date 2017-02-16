package org.javafx.ais.introductionofchanges.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Сергей
 */
@Entity
@Table(name = "CHANGES_IN_ALERT_LISTS")
public class ChangesInAlertLists implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "CHANGES_IN_ALERT_LISTS_ID")
    private long id;

    @Column(name = "IS_NEED_NOTIFICATION_YET")
    private boolean isNeedNotificationYet;

    @OneToOne
    @JoinColumn(name = "EMPLOYEE_ON_VACATION")
    private Employee employeeOnVacation;

    @OneToOne
    @JoinColumn(name = "EMPLOYEE_ACTING")
    private Employee employeeActing;

    @Column(name = "CHANGES_IN_ALERT_LISTS_START_DATE")
    private LocalDateTime startDateTime;

    @Column(name = "CHANGES_IN_ALERT_LISTS_END_DATE")
    private LocalDateTime endDateTime;

    @Column(name = "CHANGES_IN_ALERT_LISTS_GROUNDS")
    private String grounds;

    public ChangesInAlertLists() {
        isNeedNotificationYet = true;
    }

    public ChangesInAlertLists(Employee employeeOnVacation,
                               Employee employeeActing, LocalDateTime startDateTime, LocalDateTime endDateTime,
                               String grounds, Set<AlertList> alertLists) {
        this.employeeOnVacation = employeeOnVacation;
        this.employeeActing = employeeActing;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.grounds = grounds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNeedNotificationYet() {
        return isNeedNotificationYet;
    }

    public void setIsNeedNotificationYet(boolean isNeedNotificationYet) {
        this.isNeedNotificationYet = isNeedNotificationYet;
    }

    public Employee getEmployeeOnVacation() {
        return employeeOnVacation;
    }

    public void setEmployeeOnVacation(Employee employeeOnVacation) {
        this.employeeOnVacation = employeeOnVacation;
    }

    public Employee getEmployeeActing() {
        return employeeActing;
    }

    public void setEmployeeActing(Employee employeeActing) {
        this.employeeActing = employeeActing;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getGrounds() {
        return grounds;
    }

    public void setGrounds(String grounds) {
        this.grounds = grounds;
    }

    @Override
    public String toString() {
        return "Сотрудник в отпуске = " + employeeOnVacation
                + ", сотрудник исполняющий обязанности = " + employeeActing
                + ", период с = " + startDateTime.toLocalDate()
                + ", по = " + endDateTime.toLocalDate()
                + ", основания = " + grounds;
//        return "ChangesInAlertLists{" + "id=" + id
//                + ", employeeOnVacation=" + employeeOnVacation
//                + ", employeeActing=" + employeeActing
//                + ", startDateTime=" + startDateTime
//                + ", endDateTime=" + endDateTime
//                + ", grounds=" + grounds;
////                + ", alertLists=" + alertListsInChangesInAlertLists + '}';
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
        final ChangesInAlertLists other = (ChangesInAlertLists) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
