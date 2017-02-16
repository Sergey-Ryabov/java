package org.javafx.ais.introductionofchanges.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Сергей
 */
@Entity
@Table(name = "BROWSING_ITEM")
public class BrowsingItem implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "BROWSING_ITEM_ID")
    private long id;

    @OneToOne
    @JoinColumn(name = "EMPLOYEE")
    private Employee employee;

    @Column(name = "localDateTime")
    private LocalDateTime localDateTime;

    public BrowsingItem() {
    }

    public BrowsingItem(Employee employee, LocalDateTime localDateTime) {
        this.employee = employee;
        this.localDateTime = localDateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getLocalDateTime() {
//       return LocalDateTime.parse(localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "BrowsingItem{" + "id=" + id + ", employee=" + employee + ", date=" + localDateTime + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final BrowsingItem other = (BrowsingItem) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
