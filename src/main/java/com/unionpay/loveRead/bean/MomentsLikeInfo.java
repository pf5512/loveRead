package com.unionpay.loveRead.bean;

/**
 * @Desc:点赞用户信息
 * @Author: tony
 * @Date: Created in 17/8/4 下午4:41  
 */
public class MomentsLikeInfo {
    /**
     * 点赞用户id
     */
    private String   userId;

    /**
     * 点赞用户头像
     */
    private String   headIcon;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }
}
