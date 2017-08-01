package com.unionpay.loveRead.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_score_config")
public class ScoreConfig implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8162644669886747834L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    // 规则名称
    @Column(name = "rule_name")
    private String ruleName;

    // 规则值
    @Column(name = "rule_value")
    private Integer ruleValue;

    // 备注
    @Column(name = "remark")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(Integer ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
