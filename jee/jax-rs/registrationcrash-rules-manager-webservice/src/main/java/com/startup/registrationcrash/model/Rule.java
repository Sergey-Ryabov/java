package com.startup.registrationcrash.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "RULE")
public class Rule implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "RULE_ID")
    private long id;
    @OneToOne
    @JoinColumn(name = "RULE_FIRST_CA_ID")
    private CircumstancesAccident firstDriverAnswer;
    @OneToOne
    @JoinColumn(name = "RULE_SECOND_CA_ID")
    private CircumstancesAccident secondDriverAnswer;
    @Column(name = "RULE_RESULT")
    private int result;
    @Column(name = "RULE_DESCRIPTION", length = 2000)
    private String description;
    @Column(name = "RULE_ACTIONS", length = 2000)
    private String actions;

    public Rule() {
    }

    public Rule(long id, CircumstancesAccident firstDriverAnswer, CircumstancesAccident secondDriverAnswer, int result, String description, String actions) {
        this.id = id;
        this.firstDriverAnswer = firstDriverAnswer;
        this.secondDriverAnswer = secondDriverAnswer;
        this.result = result;
        this.description = description;
        this.actions = actions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CircumstancesAccident getFirstDriverAnswer() {
        return firstDriverAnswer;
    }

    public void setFirstDriverAnswer(CircumstancesAccident firstDriverAnswer) {
        this.firstDriverAnswer = firstDriverAnswer;
    }

    public CircumstancesAccident getSecondDriverAnswer() {
        return secondDriverAnswer;
    }

    public void setSecondDriverAnswer(CircumstancesAccident secondDriverAnswer) {
        this.secondDriverAnswer = secondDriverAnswer;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rule other = (Rule) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
