package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class BookDao extends HibernateBaseDao<Book, Serializable> {

    /**
     * 更新图书状态
     *
     * @param bookId
     * @param status
     */
    public int modifyBookStatus(int bookId, String status) {
        String hql = "update Book set status = ? where id = ?";
        int ret = update(hql, status, bookId);
        return ret;
    }

    /**
     * 根据用户id和书籍状态获取用户所有的书籍信息
     *
     * @param uid
     * @param bookStatus
     *
     * @return
     */
    public List<Book> findBookListByUid(String uid, String bookStatus) {
        String hql = "from Book where ownerId = ?  and status = ?";
        return find(hql, uid, bookStatus);
    }

    /**
     * 获取用户借了某一ISBN号的图书本数
     *
     * @param isbn
     * @param bookIdList 在借的图书id列表
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Book> findBorrowSameBookNums(String isbn, List<Integer> bookIdList) {
        String hql = "from Book where isbn = :isbn and id in :bookIdList ";
        // 组合参数值
        Query query = createQuery(hql);
        query.setParameter("isbn", isbn);
        query.setParameterList("bookIdList", bookIdList);
        return query.list();
    }

    /**
     * 获取用户拥有某ISBN号的所有图书
     *
     * @param uid
     * @param isbn
     *
     * @return
     */
    public List<Book> findUserBookNumsByIsbn(String uid, String isbn) {
        String hql = "from Book where ownerId = ? and isbn = ?";
        return find(hql, uid, isbn);
    }

}
