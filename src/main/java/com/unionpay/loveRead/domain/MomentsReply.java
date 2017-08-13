package com.unionpay.loveRead.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author taofangjin
 * 评论回复表
 */
@Entity
@Table(name = "tbl_moments_reply")
public class MomentsReply implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2223224471790812119L;

    /**
     * 主键自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer  id;
    
    /**
     * 回复所在的评论Id
     */
    @Column(name = "moments_id")
    private String momentsId;
    
    /**
     * 回复者id
     */
    @Column(name = "replyer_id")
    private String replyerId;
    
    /**
     * 回复者姓名
     */
    @Column(name = "replyer_name")
    private String replyerName;
    
    /**
     * 回复内容
     */
    @Column(name = "reply_content")
    private String replyContent;
    
    /**
     * 回复时间
     */
    @Column(name = "reply_time")
    private Timestamp replyTime;

    /**
     * 被回复者姓名
     */
    @Column(name = "parent_replyer_id")
    private String parentReplyerId;

    /**
     * 被回复者姓名
     */
    @Column(name = "parent_replyer_name")
    private String parentReplyerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMomentsId() {
        return momentsId;
    }

    public void setMomentsId(String momentsId) {
        this.momentsId = momentsId;
    }

    public String getReplyerId() {
        return replyerId;
    }

    public void setReplyerId(String replyerId) {
        this.replyerId = replyerId;
    }

    public String getReplyerName() {
        return replyerName;
    }

    public void setReplyerName(String replyerName) {
        this.replyerName = replyerName;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Timestamp getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Timestamp replyTime) {
        this.replyTime = replyTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getParentReplyerId() {
        return parentReplyerId;
    }

    public void setParentReplyerId(String parentReplyerId) {
        this.parentReplyerId = parentReplyerId;
    }

    public String getParentReplyerName() {
        return parentReplyerName;
    }

    public void setParentReplyerName(String parentReplyerName) {
        this.parentReplyerName = parentReplyerName;
    }
}
