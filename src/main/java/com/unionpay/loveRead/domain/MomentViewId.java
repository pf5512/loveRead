package com.unionpay.loveRead.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/8/9 下午9:51  
 */
@Embeddable
public class MomentViewId implements Serializable{

    @Column(name = "moments_id")
    private Integer momentsId;

    @Column(name = "reply_id")
    private Integer replyId;

    public Integer getMomentsId() {
        return momentsId;
    }

    public void setMomentsId(Integer momentsId) {
        this.momentsId = momentsId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof MomentViewId){
            MomentViewId key = (MomentViewId)o ;
            if(this.momentsId == key.getMomentsId() && this.replyId.equals(key.getReplyId())){
                return true ;
            }
        }
        return false ;

    }

    @Override
    public int hashCode() {
        return this.momentsId.hashCode();
    }
}
