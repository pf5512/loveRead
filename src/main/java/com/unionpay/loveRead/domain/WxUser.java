package com.unionpay.loveRead.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_wx_user_info")
public class WxUser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8162644669886747834L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    // U聊用户id
    @Column(name = "open_id")
    private String openId;

    // 是否订阅 0没订阅 1订阅
    @Column(name = "subscribe")
    private Integer subscribe = 0;

    // 昵称
    @Column(name = "nick_name")
    private String nickName;

    // 性别
    @Column(name = "sex")
    private String sex;

    // 城市
    @Column(name = "city")
    private String city;

    // 国家
    @Column(name = "country")
    private String country;

    // 省份
    @Column(name = "province")
    private String province;

    // 语言
    @Column(name = "language")
    private String language;

    // 头像
    @Column(name = "head_img_url")
    private String headImgUrl;

    //关注时间
    @Column(name = "subscribe_time")
    private Timestamp subscribeTime;

    //取消关注时间
    @Column(name = "cancle_sub_time")
    private Timestamp cancleSubTime;

    // 开放平台账号
    @Column(name = "union_id")
    private String unionId;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 分组ID
    @Column(name = "group_id")
    private Integer groupId;

    // 微信公众号id
    @Column(name = "app_id")
    private String appId;

    // 是否是黑名单用户
    @Column(name = "is_black")
    private Integer isBlack;

    // 积分
    @Column(name = "score")
    private Integer score = 0;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Integer isBlack) {
        this.isBlack = isBlack;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Timestamp getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Timestamp subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public Timestamp getCancleSubTime() {
        return cancleSubTime;
    }

    public void setCancleSubTime(Timestamp cancleSubTime) {
        this.cancleSubTime = cancleSubTime;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}