package com.startup.registrationcrash.resource.response;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Сергей
 */
//@Entity
//@Table(name = "RULE_RESPONSE_HEADER")
public class RuleResponseHeader implements ResponseHeader, Serializable {

//    "id:�����", "result:���������", "description:��������", "actions:��������"
//    @Id
//    @GeneratedValue
//    @Column(name = "RULE_RESPONSE_HEADER_ID")
//    private long ruleResponseHeaderId;
//    @Column(name = "RULE_ID", length = 100)
    private String id;
//    @Column(name = "RULE_RESULT", length = 100)
    private String result;
//    @Column(name = "RULE_DESCRIPTION", length = 2000)
    private String description;
//    @Column(name = "RULE_ACTIONS", length = 2000)
    private String actions;

    public RuleResponseHeader() {
    }
    
     public RuleResponseHeader(String id, String result, String description, String actions) {
        this.id = id;
        this.result = result;
        this.description = description;
        this.actions = actions;
    }

//    public RuleResponseHeader(long ruleResponseHeaderId, String id, String result, String description, String actions) {
//        this.ruleResponseHeaderId = ruleResponseHeaderId;
//        this.id = id;
//        this.result = result;
//        this.description = description;
//        this.actions = actions;
//    }

//    public long getRuleResponseHeaderId() {
//        return ruleResponseHeaderId;
//    }
//
//    public void setRuleResponseHeaderId(long ruleResponseHeaderId) {
//        this.ruleResponseHeaderId = ruleResponseHeaderId;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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

}
