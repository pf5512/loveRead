package com.unionpay.loveRead.bean;

import com.unionpay.loveRead.domain.MomentsReply;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 圈子单条状态信息
 * @author taofangjin
 *
 */
public class MomentsInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 信息id
     */
    private Integer   momentId;

    /**
     * 用户id
     */
    private String   userId;
    
    /**
     * 用户姓名
     */
    private String   userName;
    
    /**
     * 用户头像
     */
    private String   userIcon;
    
    /**
     * 发表时间
     */
    private Timestamp   crtTs;
    
    /**
     * 发布信息针对的图书id
     */
    private Integer   bookId;
    
    /**
     * 发布信息针对的图书名称
     */
    private String   bookName;
    
    /**
     * 发布的具体内容
     */
    private String   content;

    /**
     * 发布的图片
     */
    private String  momentsImg;

    /**
     * 状态的点赞信息列表
     */
    private List<MomentsLikeInfo>  momentsLikeList;

    /**
     * 状态的回复信息列表
     */
    private List<MomentsReply>  momentsReplyList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMomentId() {
        return momentId;
    }

    public void setMomentId(Integer momentId) {
        this.momentId = momentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public Timestamp getCrtTs() {
        return crtTs;
    }

    public void setCrtTs(Timestamp crtTs) {
        this.crtTs = crtTs;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMomentsImg() {
        return momentsImg;
    }

    public void setMomentsImg(String momentsImg) {
        this.momentsImg = momentsImg;
    }

    public List<MomentsLikeInfo> getMomentsLikeList() {
        return momentsLikeList;
    }

    public void setMomentsLikeList(List<MomentsLikeInfo> momentsLikeList) {
        this.momentsLikeList = momentsLikeList;
    }

    public List<MomentsReply> getMomentsReplyList() {
        return momentsReplyList;
    }

    public void setMomentsReplyList(List<MomentsReply> momentsReplyList) {
        this.momentsReplyList = momentsReplyList;
    }
}
