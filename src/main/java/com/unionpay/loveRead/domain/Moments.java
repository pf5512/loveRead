package com.unionpay.loveRead.domain;


import com.alibaba.fastjson.annotation.JSONField;
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
    private Integer bookId;

    /**
     * 评论的图书名
     */
    @Column(name = "book_name")
    private String bookName;

    /**
     * 发表的内容
     */
    @Column(name = "moments_content")
    private String momentsContent;

    /**
     * 发表的图片
     */
    @Column(name = "moments_img")
    private String momentsImg;

    /**
     * 发表的时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "crt_ts")
    private Timestamp crtTs;

    /**
     * 消息状态
     */
    @Column(name = "moments_st")
    private String  momentsSt = Constants.MOMENTS_STATUS_NORMAL;

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

    public String getMomentsContent() {
        return momentsContent;
    }

    public void setMomentsContent(String momentsContent) {
        this.momentsContent = momentsContent;
    }

    public String getMomentsImg() {
        return momentsImg;
    }

    public void setMomentsImg(String momentsImg) {
        this.momentsImg = momentsImg;
    }

    public Timestamp getCrtTs() {
        return crtTs;
    }

    public void setCrtTs(Timestamp crtTs) {
        this.crtTs = crtTs;
    }

    public String getMomentsSt() {
        return momentsSt;
    }

    public void setMomentsSt(String momentsSt) {
        this.momentsSt = momentsSt;
    }
}
