package com.unionpay.loveRead.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "book_info_view")
public class BookInfoView implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -944004303715848545L;
    @Id
    @Column(name = "book_id")
    private Integer bookId;

    // 书名
    @Column(name = "book_name")
    private String bookName;

    // 书主id
    @Column(name = "owner_id")
    private String ownerId;

    // 书本isbn号
    @Column(name = "isbn")
    private String isbn;

    // 封面
    @Column(name = "cover")
    private String cover;

    // 借阅状态
    @Column(name = "status")
    private String status;

    // 借阅类型
    @Column(name = "borrow_type")
    private String borrowType;

    // 借阅期限
    @Column(name = "last_day")
    private Integer lastDay;

    // 交换条件
    @Column(name = "exchange_condition")
    private String exchangeCondition;

    // 图书概要信息
    @Column(name = "summary")
    private String summary;

    // 书本分类
    @Column(name = "category_name")
    private String categoryName;

    // 书主姓名
    @Column(name = "owner_name")
    private String ownerName;

    /**
     * 点赞数
     */
    @Column(name = "like_nums")
    private Integer likeNums;

    /**
     * 借阅数
     */
    @Column(name = "borrow_nums")
    private Integer borrowNums;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
