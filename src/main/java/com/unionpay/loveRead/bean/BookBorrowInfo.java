package com.unionpay.loveRead.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 图书借阅信息
 *
 * @author taofangjin
 */
public class BookBorrowInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String readerId;

    /**
     * 用户姓名
     */
    private String readerName;

    /**
     * 用户团队
     */
    private String readerTeam;

    /**
     * 图书id
     */
    private String bookId;

    /**
     * 借阅时间
     */
    private Timestamp readTime;

    /**
     * 归还时间
     */
    private Timestamp returnTime;

    /**
     * 到期时间
     */
    private Timestamp endTime;


    /**
     * 借阅状态
     */
    private String bookStatus;

    public BookBorrowInfo(String bookId) {
        this.bookId = bookId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderTeam() {
        return readerTeam;
    }

    public void setReaderTeam(String readerTeam) {
        this.readerTeam = readerTeam;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Timestamp getReadTime() {
        return readTime;
    }

    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }

    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
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
