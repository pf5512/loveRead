package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.constants.Constants;
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

    /**
     * 获取用户在借列表
     *
     * @param readerId
     *
     * @return
     */
    public List<BookBorrowFlow> findUserBorrowList(String readerId) {
        String hql = "from BookBorrowFlow where readerId = ? and status = ?";
        List<BookBorrowFlow> recordList = find(hql, readerId, Constants.BORROW_ING);
        return recordList;
    }

    /**
     * 获取用户借阅历史
     *
     * @param readerId
     *
     * @return
     */
    public List<BookBorrowFlow> findUserBorrowHistory(String readerId) {
        String hql = "from BookBorrowFlow where readerId = ? order by outTime desc";
        List<BookBorrowFlow> recordList = find(hql, readerId);
        return recordList;
    }
}
