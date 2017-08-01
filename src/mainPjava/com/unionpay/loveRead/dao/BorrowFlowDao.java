package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.domain.BookBorrowFlow;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;


@Repository
public class BorrowFlowDao extends HibernateBaseDao<BookBorrowFlow, Serializable> {

    /**
     * 获取用户某本书的借阅状态
     *
     * @param readerId
     * @param bookId
     * @param status
     *
     * @return
     */
    public BookBorrowFlow findRecordById(String readerId, int bookId, String status) {
        String hql = "from BookBorrowFlow where readerId = ? and bookId = ? and status = ?";
        List<BookBorrowFlow> recordList = find(hql, readerId, bookId, status);
        if (recordList.size() > 0) {
            return recordList.get(0);
        }
        return null;
    }
}
