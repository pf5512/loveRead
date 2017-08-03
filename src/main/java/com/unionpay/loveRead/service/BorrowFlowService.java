package com.unionpay.loveRead.service;

import com.unionpay.loveRead.dao.BorrowFlowDao;
import com.unionpay.loveRead.domain.BookBorrowFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BorrowFlowService {

    private static Logger logger = LoggerFactory.getLogger(BorrowFlowService.class);

    @Autowired
    BorrowFlowDao borrowFlowDao;

    /**
     * 获取用户在借的图书列表
     *
     * @param uid
     *
     * @return
     */
    public List<BookBorrowFlow> getUserBorrowList(String uid) {
        return borrowFlowDao.findUserBorrowList(uid);
    }

    /**
     * 获取用户的借阅历史
     * @param uid
     * @return
     */
    public List<BookBorrowFlow> getUserBorrowHistory(String uid) {
        return borrowFlowDao.findUserBorrowHistory(uid);
    }

}
