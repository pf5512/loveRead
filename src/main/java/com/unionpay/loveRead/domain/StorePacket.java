package com.unionpay.loveRead.domain;

import com.unionpay.loveRead.constants.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_spdbccc_store_packet")
public class StorePacket implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2223224471790812119L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    // 红包Id
    @Column(name = "packet_id")
    private String packetId;

    // 红包主人ID
    @Column(name = "user_id")
    private String userId;

    // 分享的红包URL
    @Column(name = "packet_url")
    private String packetUrl;

    // 剩余可分享次数
    @Column(name = "left_counts")
    private Integer left_counts = 5;

    //存储时间
    @Column(name = "crt_times")
    private Timestamp createTime;

    //红包是否有效 0有效 1无效
    @Column(name = "is_valid")
    private String isValid = Constants.PACKET_VALID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPacketUrl() {
        return packetUrl;
    }

    public void setPacketUrl(String packetUrl) {
        this.packetUrl = packetUrl;
    }

    public Integer getLeft_counts() {
        return left_counts;
    }

    public void setLeft_counts(Integer left_counts) {
        this.left_counts = left_counts;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
