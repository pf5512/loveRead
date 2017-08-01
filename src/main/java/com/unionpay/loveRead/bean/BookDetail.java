package com.unionpay.loveRead.bean;


import com.unionpay.loveRead.domain.BookInfoView;

import java.io.Serializable;
import java.sql.Timestamp;

public class BookDetail implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //图书编号
    private Integer bookId;

    // 书名
    private String bookName;

    // 书主id
    private String ownerId;

    // 书本isbn号
    private String isbn;

    // 封面
    private String cover;

    // 借阅状态
    private String status;

    // 借阅类型
    private String borrowType;

    // 借阅期限
    private Integer lastDay;

    // 交换条件
    private String exchangeCondition;

    // 图书概要信息
    private String summary;

    // 书本分类
    private String categoryName;

    // 书主姓名
    private String ownerName;

    // 书主团队
    private String ownerTeam;

    /**
     * 点赞数
     */
    private Integer likeNums;

    /**
     * 借阅数
     */
    private Integer borrowNums;

    // 读者编号
    private String readerId;

    // 借出时间
    private Timestamp outTime;

    // 到期时间
    private Timestamp endTime;

    // 归还时间
    private Timestamp backTime;

    // 读者姓名
    private String readerName;

    // 读者团队
    private String readerTeam;

    public BookDetail(BookInfoView bookInfo) {

        this.bookId = bookInfo.getBookId();
        this.bookName = bookInfo.getBookName();
        this.ownerId = bookInfo.getOwnerId();
        this.isbn = bookInfo.getIsbn();
        this.cover = bookInfo.getCover();
        this.status = bookInfo.getStatus();
        this.borrowType = bookInfo.getBorrowType();
        this.lastDay = bookInfo.getLastDay();
        this.exchangeCondition = bookInfo.getExchangeCondition();
        this.summary = bookInfo.getSummary();
        this.categoryName = bookInfo.getCategoryName();
        this.ownerName = bookInfo.getOwnerName();
        this.likeNums = bookInfo.getLikeNums();
        this.borrowNums = bookInfo.getBorrowNums();
    }


    /**
     * 根据bookInfo和borrowInfo构造BookDetail
     *
     * @param bookInfo
     * @param borrowInfo
     */
    public BookDetail(BookInfoView bookInfo, BookBorrowInfo borrowInfo) {
        this.bookId = bookInfo.getBookId();
        this.bookName = bookInfo.getBookName();
        this.ownerId = bookInfo.getOwnerId();
        this.isbn = bookInfo.getIsbn();
        this.cover = bookInfo.getCover();
        this.status = bookInfo.getStatus();
        this.borrowType = bookInfo.getBorrowType();
        this.lastDay = bookInfo.getLastDay();
        this.exchangeCondition = bookInfo.getExchangeCondition();
        this.summary = bookInfo.getSummary();
        this.categoryName = bookInfo.getCategoryName();
        this.ownerName = bookInfo.getOwnerName();
        this.likeNums = bookInfo.getLikeNums();
        this.borrowNums = bookInfo.getBorrowNums();

        this.readerId = borrowInfo.getReaderId();
        this.readerName = borrowInfo.getReaderName();
        this.readerTeam = borrowInfo.getReaderTeam();
        this.outTime = borrowInfo.getReadTime();
        this.endTime = borrowInfo.getEndTime();
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public Integer getLastDay() {
        return lastDay;
    }

    public void setLastDay(Integer lastDay) {
        this.lastDay = lastDay;
    }

    public String getExchangeCondition() {
        return exchangeCondition;
    }

    public void setExchangeCondition(String exchangeCondition) {
        this.exchangeCondition = exchangeCondition;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Integer getLikeNums() {
        return likeNums;
    }

    public void setLikeNums(Integer likeNums) {
        this.likeNums = likeNums;
    }

    public Integer getBorrowNums() {
        return borrowNums;
    }

    public void setBorrowNums(Integer borrowNums) {
        this.borrowNums = borrowNums;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public Timestamp getOutTime() {
        return outTime;
    }

    public void setOutTime(Timestamp outTime) {
        this.outTime = outTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getBackTime() {
        return backTime;
    }

    public void setBackTime(Timestamp backTime) {
        this.backTime = backTime;
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
}
