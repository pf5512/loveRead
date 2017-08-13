package com.unionpay.loveRead.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "moments_view")
public class MomentsView implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -944004303715848545L;
    @Id
    private MomentViewId momentViewId;

    /**
     * 发表者uid
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 发表者名称
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 发表者头像
     */
    @Column(name = "user_icon")
    private String userIcon;

    /**
     * 发表内容
     */
    @Column(name = "moment_content")
    private String momentContent;

    /**
     * 发表的图片
     */
    @Column(name = "moment_img")
    private String momentImg;

    /**
     * 评论的图书id
     */
    @Column(name = "book_id")
    private Integer bookId;

    /**
     * 评论的图书名
     */
    @Column(name = "book_name")
    private String bookName;

    /**
     * 发表的时间
     */
    @Column(name = "crt_ts")
    private Timestamp crtTs;

    /**
     * 消息状态
     */
    @Column(name = "moment_st")
    private String  momentSt;

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
     * 被回复者姓名
     */
    @Column(name = "parent_replyer_id")
    private String parentReplyerId;

    /**
     * 被回复者姓名
     */
    @Column(name = "parent_replyer_name")
    private String parentReplyerName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMomentContent() {
        return momentContent;
    }

    public void setMomentContent(String momentContent) {
        this.momentContent = momentContent;
    }

    public String getMomentImg() {
        return momentImg;
    }

    public void setMomentImg(String momentImg) {
        this.momentImg = momentImg;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Timestamp getCrtTs() {
        return crtTs;
    }

    public void setCrtTs(Timestamp crtTs) {
        this.crtTs = crtTs;
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

    public String getMomentSt() {
        return momentSt;
    }

    public void setMomentSt(String momentSt) {
        this.momentSt = momentSt;
    }

    public MomentViewId getMomentViewId() {
        return momentViewId;
    }

    public void setMomentViewId(MomentViewId momentViewId) {
        this.momentViewId = momentViewId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }
}
