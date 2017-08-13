package com.unionpay.loveRead.domain;


import com.unionpay.loveRead.constants.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/8/4 下午4:53  
 */
@Entity
@Table(name = "tbl_moments")
public class Moments implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2223224471790812119L;

    /**
     * 评论id,主键自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer  id;

    /**
     * 发表者uid
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 评论的图书名id
     */
    @Column(name = "book_id")
    private String bookId;

    /**
     * 评论的图书名
     */
    @Column(name = "book_name")
    private String bookName;

    /**
     * 发表的内容
     */
    @Column(name = "moment_content")
    private String momentContent;

    /**
     * 发表的图片
     */
    @Column(name = "moment_img")
    private String momentImg;

    /**
     * 发表的时间
     */
    @Column(name = "crt_ts")
    private Timestamp crtTs;

    /**
     * 消息状态
     */
    @Column(name = "moment_st")
    private String  momentSt = Constants.MOMENTS_STATUS_NORMAL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public Timestamp getCrtTs() {
        return crtTs;
    }

    public void setCrtTs(Timestamp crtTs) {
        this.crtTs = crtTs;
    }

    public String getMomentSt() {
        return momentSt;
    }

    public void setMomentSt(String momentSt) {
        this.momentSt = momentSt;
    }
}
