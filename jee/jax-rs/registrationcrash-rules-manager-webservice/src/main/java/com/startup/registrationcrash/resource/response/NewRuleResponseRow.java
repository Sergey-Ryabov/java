package com.startup.registrationcrash.resource.response;

import java.util.Objects;

/**
 *
 * @author Сергей
 */
public class NewRuleResponseRow implements ResponseHeader {

    private String firstDriverAnswer;
    private String actionName;
    private String secondDriverAnswer;

    public NewRuleResponseRow() {
    }

    public NewRuleResponseRow(String firstDriverAnswer, String description, String secondDriverAnswer) {
        this.firstDriverAnswer = firstDriverAnswer;
        this.actionName = description;
        this.secondDriverAnswer = secondDriverAnswer;
    }

       public String getFirstDriverAnswer() {
        return firstDriverAnswer;
    }

    public void setFirstDriverAnswer(String firstDriverAnswer) {
        this.firstDriverAnswer = firstDriverAnswer;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getSecondDriverAnswer() {
        return secondDriverAnswer;
    }

    public void setSecondDriverAnswer(String secondDriverAnswer) {
        this.secondDriverAnswer = secondDriverAnswer;
    }

   

    @Override
    public String toString() {
        return "NewRuleResponseHeader{firstDriverAnswer=" + firstDriverAnswer + ", actionName=" + actionName + ", secondDriverAnswer=" + secondDriverAnswer + '}';
    }

}
