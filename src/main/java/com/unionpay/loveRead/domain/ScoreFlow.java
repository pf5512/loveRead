package com.unionpay.loveRead.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_score_flow")
public class ScoreFlow implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3282904243364664926L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    // 读者编号
    @Column(name = "uid")
    private String uid;

    //触发得分行为
    @Column(name = "action")
    private String action;

    // 所获积分
    @Column(name = "score")
    private Integer score;

    // 得分日期
    @Column(name = "score_date")
    private String scoreDate;

    // 记录创建时间戳
    @Column(name = "rec_crt_ts")
    private Timestamp recCrtTs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getScoreDate() {
        return scoreDate;
    }

    public void setScoreDate(String scoreDate) {
        this.scoreDate = scoreDate;
    }

    public Timestamp getRecCrtTs() {
        return recCrtTs;
    }

    public void setRecCrtTs(Timestamp recCrtTs) {
        this.recCrtTs = recCrtTs;
    }

}
