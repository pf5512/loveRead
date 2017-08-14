package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.MomentsView;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/8/9 下午9:09  
 */
@Repository
public class MomentsViewDao extends HibernateBaseDao<MomentsView, Serializable> {

    public List<MomentsView> findNormalMomentsViewList() {
        String hql = "from MomentsView where momentsSt = ? and replySt = ?";
        return find(hql, Constants.MOMENTS_STATUS_NORMAL,Constants.MOMENTS_STATUS_NORMAL);
    }
}
