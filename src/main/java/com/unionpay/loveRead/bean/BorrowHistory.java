package com.unionpay.loveRead.bean;

import java.io.Serializable;

public class BorrowHistory implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 图书id
     */
    private String bookId;

    /**
     * 借阅时间
     */
    private String readTime;

    /**
     * 归还时间
     */
    private String returnTime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }
}
