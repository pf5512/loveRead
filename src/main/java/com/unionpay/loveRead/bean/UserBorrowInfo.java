package com.unionpay.loveRead.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 个人借阅信息
 *
 * @author taofangjin
 */
public class UserBorrowInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 图书id
     */
    private String bookId;

    /**
     * 图书名
     */
    private String bookName;

    /**
     * 图书封面
     */
    private String cover;

    /**
     * 图书主人ID
     */
    private String ownerId;

    /**
     * 图书主人姓名
     */
    private String ownerName;

    /**
     * 图书主人所在团队
     */
    private String ownerTeam;

    /**
     * 借阅时间
     */
    private Timestamp outTime;

    /**
     * 归还时间
     */
    private Timestamp backTime;

    /**
     * 到期时间
     */
    private Timestamp endTime;

    /**
     * 借阅状态
     */
    private String bookStatus;


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerTeam() {
        return ownerTeam;
    }

    public void setOwnerTeam(String ownerTeam) {
        this.ownerTeam = ownerTeam;
    }

    public Timestamp getOutTime() {
        return outTime;
    }

    public void setOutTime(Timestamp outTime) {
        this.outTime = outTime;
    }

    public Timestamp getBackTime() {
        return backTime;
    }

    public void setBackTime(Timestamp backTime) {
        this.backTime = backTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }
}
