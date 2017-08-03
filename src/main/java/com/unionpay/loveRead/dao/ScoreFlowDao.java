package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.domain.ScoreFlow;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class ScoreFlowDao extends HibernateBaseDao<ScoreFlow, Serializable> {

    @SuppressWarnings("unchecked")
    public String queryDayLimitUsed(String uid, String date) {
        String sql = "select sum(score) from ScoreFlow where uid = ? and scoreDate = ?";
        List<String> list = this.createQuery(sql, uid, date).list();
        if (list != null && list.size() > 0 && list.get(0) != null) {
            return String.valueOf(list.get(0));
        }
        return "0";
    }

    public List<ScoreFlow> queryAllByUid(String uid) {
        String hql = "from ScoreFlow  where uid = ?  order by recCrtTs desc";
//		String hql = "from ScoreFlow  where uid = ? and score <> 0 order by recCrtTs desc";
        return find(hql, uid);
    }
}
