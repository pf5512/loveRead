package com.unionpay.loveRead.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_book_borrow_flow")
public class BookBorrowFlow implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 672423021222287219L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    // 读者编号
    @Column(name = "reader_id")
    private String readerId;

    // 书id
    @Column(name = "book_id")
    private Integer bookId;

    // 借出时间
    @Column(name = "out_time")
    private Timestamp outTime;

    // 到期时间
    @Column(name = "end_time")
    private Timestamp endTime;

    // 归还时间
    @Column(name = "back_time")
    private Timestamp backTime;

    // 借阅状态
    @Column(name = "status")
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
