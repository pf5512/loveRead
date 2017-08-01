package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.domain.BookLikeCount;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class BookLikeCountDao extends HibernateBaseDao<BookLikeCount, Serializable> {

    /**
     * 增加图书点赞总数
     *
     * @param bookId
     */
    public int addBookLikeCounts(int bookId) {
        String hql = "update BookLikeCount set likeNums = likeNums + 1 where bookId = ?";
        int ret = update(hql, bookId);
        return ret;
    }

    /**
     * 减少图书点赞总数
     *
     * @param bookIdInt
     */
    public int minusBookLikeCounts(int bookIdInt) {
        String hql = "update BookLikeCount set likeNums = likeNums - 1 where bookId = ?";
        int ret = update(hql, bookIdInt);
        return ret;
    }

}
