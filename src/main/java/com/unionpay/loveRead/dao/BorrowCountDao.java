package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.domain.BookBorrowCount;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;


@Repository
public class BorrowCountDao extends HibernateBaseDao<BookBorrowCount, Serializable> {

    /**
     * 增加图书借阅总数
     *
     * @param bookIdInt
     */
    public int addBookBorrowCounts(int bookIdInt) {
        String hql = "update BookBorrowCount set borrowNums = borrowNums + 1 where bookId = ?";
        int ret = update(hql, bookIdInt);
        return ret;
    }
}
