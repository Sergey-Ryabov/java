package org.javafx.ais.introductionofchanges.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Сергей
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "EMPLOYEE_ID")
public class SystemUser extends Employee {

    @Column(name = "LOGIN", unique = true)
    private String login;
    @Column(name = "PASSWORD")
    private String password;

    public SystemUser() {
    }

    public SystemUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString();
//        return super.toString() + " SystemUser{" + "login=" + login + '}';
    }

}
