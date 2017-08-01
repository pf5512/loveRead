package com.unionpay.loveRead.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_book_borrow_count")
public class BookBorrowCount implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3363606981854491380L;

    @Id
    @Column(name = "book_id")
    private Integer bookId;

    //点赞数
    @Column(name = "borrow_nums")
    private Integer borrowNums;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBorrowNums() {
        return borrowNums;
    }

    public void setBorrowNums(Integer borrowNums) {
        this.borrowNums = borrowNums;
    }
}
