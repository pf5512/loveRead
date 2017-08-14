package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.Moments;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/8/9 下午9:09  
 */
@Repository
public class MomentsDao extends HibernateBaseDao<Moments, Serializable> {

    /**
     * 更新消息状态
     * @param momentsId
     */
    public void updateMomentsStatus(String momentsId) {
        int momentsIdInt = Integer.valueOf(momentsId);
        String hql = "update Moments set momentsSt =  ? where id = ?";
        update(hql, Constants.MOMENTS_STATUS_DELETED,momentsIdInt);
    }
}
