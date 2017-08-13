package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.domain.BookBorrowFlow;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
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

    /**
     * 查询用户借阅过的图书
     * 这里程序去重，不通过distinct，提高效率
     * @param readerId
     * @return
     */
    public List<Book> findBorrowedBookList(String readerId){
        List<Book> bookList = new ArrayList<>();
        String hql = "select b.id,b.book_name from tbl_book_borrow_flow bf " +
                " left join tbl_book b on bf.book_id = b.id " +
                " where bf.reader_id = ? ";
        Query query = createSQLQuery(hql, readerId);
        List<Object[]> resultList = query.list();
        for(Object[] obj:resultList){
            Book book = new Book();
            book.setId(Integer.parseInt(obj[0].toString()));
            book.setBookName(obj[1].toString());
            bookList.add(book);
        }
        return bookList;
    }

}
