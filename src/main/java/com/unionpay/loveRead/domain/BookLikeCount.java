package com.unionpay.loveRead.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_book_like_count")
public class BookLikeCount implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3363606981854491380L;

    @Id
    @Column(name = "book_id")
    private Integer bookId;

    //点赞数
    @Column(name = "like_nums")
    private Integer likeNums;


    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getLikeNums() {
        return likeNums;
    }

    public void setLikeNums(Integer likeNums) {
        this.likeNums = likeNums;
    }
}
