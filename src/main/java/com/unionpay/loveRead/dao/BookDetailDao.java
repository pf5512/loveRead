package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.BookInfoView;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class BookDetailDao extends HibernateBaseDao<BookInfoView, Serializable> {

    /**
     * 根据搜索条件模糊匹配书名(可借或借出)
     *
     * @param keywords
     *
     * @return
     */
    public List<BookInfoView> findBookListByKeywords(String keywords) {
        String hql = "from BookInfoView where status in (?,?) ";
        if (keywords != null) {
            hql = hql + " and (bookName like ? or ownerName like ?)";
            keywords = "%" + keywords + "%";
            return find(hql, Constants.BOOK_STATUS_AVAILABLE,
                    Constants.BOOK_STATUS_OUT, keywords, keywords);
        }
        return find(hql, Constants.BOOK_STATUS_AVAILABLE,
                Constants.BOOK_STATUS_OUT);
    }

    /**
     * 根据图书借阅类别（借/换）查询图书详情列表
     *
     * @param borrowType
     *
     * @return
     */
    public List<BookInfoView> findBookListByBorrowType(String borrowType) {
        String hql = "from BookInfoView where borrowType = ? and status in (?,?) ";
        return find(hql, borrowType, Constants.BOOK_STATUS_AVAILABLE,
                Constants.BOOK_STATUS_OUT);
    }

    /**
     * 获取某个用户所有的书籍信息
     *
     * @param uid
     *
     * @return
     */
    public List<BookInfoView> findBookListByUid(String uid) {
        String hql = "from BookInfoView where ownerId = ?";
        return find(hql, uid);
    }

    /**
     * 按照点赞数排行
     *
     * @param numInt
     *
     * @return
     */
    public List<BookInfoView> findBookListOrderByLikes(int numInt) {
        String hql = "from BookInfoView order by likeNums desc ";
        if (numInt > 0) {
            return findPage(hql, 0, numInt);
        }
        return find(hql);
    }

    /**
     * 按照借阅数排行
     *
     * @param numInt
     *
     * @return
     */
    public List<BookInfoView> findBookListOrderByBorrowNums(int numInt) {
        String hql = "from BookInfoView order by borrowNums desc ";
        if (numInt > 0) {
            return findPage(hql, 0, numInt);
        }
        return find(hql);
    }

    /**
     * 获取在借的图书
     *
     * @return
     */
    public List<BookInfoView> findOutBookList() {
        String hql = "from BookInfoView where status = ?";
        return find(hql, Constants.BOOK_STATUS_OUT);
    }

    /**
     * 所有图书页面使用，分页搜索所有图书
     *
     * @param start
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BookInfoView> findBookListByLimits(Integer start, String type) {
        String hql = "from BookInfoView where status in (?,?) ";
        Query query = null;
        if (StringUtils.isBlank(type)) {
            if (start != null)
                query = createQuery(hql, Constants.BOOK_STATUS_AVAILABLE,
                        Constants.BOOK_STATUS_OUT).setFirstResult(start)
                        .setMaxResults(Constants.PAGE_SIZE);
            else
                query = createQuery(hql, Constants.BOOK_STATUS_AVAILABLE,
                        Constants.BOOK_STATUS_OUT);
        } else {
            hql += " and borrowType = ?";
            if (start != null)
                query = createQuery(hql, Constants.BOOK_STATUS_AVAILABLE,
                        Constants.BOOK_STATUS_OUT, type).setFirstResult(start)
                        .setMaxResults(Constants.PAGE_SIZE);
            else
                query = createQuery(hql, Constants.BOOK_STATUS_AVAILABLE,
                        Constants.BOOK_STATUS_OUT, type);
        }
        return query.list();
    }

    /**
     * 获取猜你喜欢列表
     *
     * @param numInt
     *
     * @return
     */
    public List<BookInfoView> findLikeBookListOrderByLikes(int numInt) {
        String hql = "from BookInfoView where status in (?,?) order by likeNums desc ";
        if (numInt > 0) {
            return findPage(hql, 0, numInt, Constants.BOOK_STATUS_AVAILABLE,
                    Constants.BOOK_STATUS_OUT);
        }
        return find(hql, Constants.BOOK_STATUS_AVAILABLE,
                Constants.BOOK_STATUS_OUT);
    }
}
