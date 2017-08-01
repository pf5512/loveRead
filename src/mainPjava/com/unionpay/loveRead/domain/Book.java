package com.unionpay.loveRead.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_book")
public class Book implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2223224471790812119L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    // 书名
    @Column(name = "book_name")
    private String bookName;

    // 书主id
    @Column(name = "owner_id")
    private String ownerId;

    // 书主团队
    @Column(name = "owner_team")
    private String ownerTeam;

    // 书本isbn号
    @Column(name = "isbn")
    private String isbn;

    // 书本分类id
    @Column(name = "category_id")
    private Integer categoryId;

    // 封面
    @Column(name = "cover")
    private String cover;

    // 借阅状态
    @Column(name = "status")
    private String status = "00";

    // 借阅类型
    @Column(name = "borrow_type")
    private String borrowType = "00";

    // 图书创建时间
    @Column(name = "create_time")
    private Timestamp createTime;

    // 图书更新时间
    @Column(name = "update_time")
    private Timestamp updateTime;

    // 借阅期限
    @Column(name = "last_day")
    private Integer lastDay = 30;

    // 图书概要信息
    @Column(name = "summary")
    private String summary;

    // 出版社信息
    @Column(name = "publisher")
    private String publisher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOwnerTeam() {
        return ownerTeam;
    }

    public void setOwnerTeam(String ownerTeam) {
        this.ownerTeam = ownerTeam;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLastDay() {
        return lastDay;
    }

    public void setLastDay(Integer lastDay) {
        this.lastDay = lastDay;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
