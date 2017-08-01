package com.unionpay.loveRead.service;


import com.unionpay.loveRead.dao.ScoreFlowDao;
import com.unionpay.loveRead.domain.ScoreFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ScoreFlowService {

    @Autowired
    private ScoreFlowDao scoreFlowDao;

    /**
     * 查询用户某日已获得的积分总量
     *
     * @param uid
     * @param date
     *
     * @return
     */
    public String getDayLimitUsed(String uid, String date) {
        return scoreFlowDao.queryDayLimitUsed(uid, date);
    }

    /**
     * 新增一条积分记录
     *
     * @param scoreFlow
     */
    public void addOneRec(ScoreFlow scoreFlow) {
        scoreFlowDao.save(scoreFlow);
    }

    /**
     * 查询用户积分历史数据
     *
     * @param uid
     *
     * @return
     */
    public List<ScoreFlow> getAllByUid(String uid) {
        return scoreFlowDao.queryAllByUid(uid);
    }

}
