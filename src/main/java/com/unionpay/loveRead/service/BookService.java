package com.unionpay.loveRead.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.loveRead.bean.BookBorrowInfo;
import com.unionpay.loveRead.bean.BookDetail;
import com.unionpay.loveRead.bean.BorrowHistory;
import com.unionpay.loveRead.constants.AppConfig;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.dao.BookDao;
import com.unionpay.loveRead.dao.BookDetailDao;
import com.unionpay.loveRead.dao.BookLikeCountDao;
import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.domain.BookInfoView;
import com.unionpay.loveRead.domain.BookLikeCount;
import com.unionpay.loveRead.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BookService {

    private static Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    AppConfig appConfig;

    @Autowired
    BookDao bookDao;

    @Autowired
    BookDetailDao bookDetailDao;

    @Autowired
    BookLikeCountDao bookLikeCountDao;


    /**
     * 根据图书id查询唯一图书
     *
     * @param bookId
     *
     * @return
     */
    public Book getBookById(String bookId) {
        logger.info("----------查询图书...");
        int bookIdInt = Integer.valueOf(bookId);
        return bookDao.get(bookIdInt);
    }

    /**
     * 添加图书
     *
     * @param book
     */
    public void addBook(Book book) {
        try {
            bookDao.save(book);
            //新增图书点赞总数
            BookLikeCount likeCount = new BookLikeCount();
            likeCount.setBookId(book.getId());
            likeCount.setLikeNums(0);
            bookLikeCountDao.save(likeCount);
            //TODO 新增图书时增加积分
        } catch (Exception e) {
            logger.error("图书添加失败！");
        }

    }

    /**
     * 修改图书
     *
     * @param book
     */
    public void updateBook(Book book) {
        bookDao.modify(book);
    }


    /**
     * 更新图书状态
     *
     * @param id
     * @param status
     */
    public void updateBookStatus(String id, String status) {
        logger.info("----------更新图书状态...");
        int bookId = Integer.valueOf(id);
        int ret = bookDao.modifyBookStatus(bookId, status);
        if (ret > 0) {
            logger.info("更新" + bookId + "成功！");
        } else {
            logger.info("更新" + bookId + "失败！");
        }
    }

    /**
     * 根据id删除书籍
     *
     * @param id
     */
    @CacheEvict(value = "myCache", key = "#id")
    public void delById(int id) {
        bookDao.delete(id);
        //TODO 删除图书时扣分

    }

    /**
     * 根据用户id和书籍状态获取用户所有的书籍信息
     *
     * @param uid
     * @param bookStatus
     *
     * @return
     */
    public List<Book> getBookListByUid(String uid, String bookStatus) {
        return bookDao.findBookListByUid(uid, bookStatus);
    }

    /**
     * 根据搜索条件获取图书信息
     *
     * @param keywords
     *
     * @return
     */
    public List<BookInfoView> getBookListByKeywords(String keywords) {
        List<BookInfoView> bookDetailList = bookDetailDao.findBookListByKeywords(keywords);
        return bookDetailList;
    }

    /**
     * 根据某个ID，查询图书详情
     *
     * @param bookId
     *
     * @return
     */
    public BookInfoView getBookDetailByBookId(String bookId) {
        logger.info("----------查询图书详情...");
        int bookIdInt = Integer.parseInt(bookId);
        BookInfoView bookDetail = bookDetailDao.get(bookIdInt);
        return bookDetail;
    }

    /**
     * 根据图书借阅类别（借/换）查询图书详情列表
     *
     * @param borrowType
     *
     * @return
     */
    public List<BookInfoView> getBookListByBorrowType(String borrowType) {
        List<BookInfoView> bookDetailList = bookDetailDao.findBookListByBorrowType(borrowType);
        return bookDetailList;
    }


    /**
     * 获取某个用户所有的书籍信息
     *
     * @param uid
     *
     * @return
     */
    public List<BookInfoView> getBooInfoListByUid(String uid) {
        List<BookInfoView> bookDetailList = bookDetailDao.findBookListByUid(uid);
        return bookDetailList;
    }

    /**
     * 根据ISBN号查询图书信息
     *
     * @param isbn
     *
     * @return
     */
    public Book searchBookInfoByIsbn(String isbn) {
        //查询图书信息
        String queryUrl = appConfig.getDoubanApiUrl() + isbn;
        String result = "";
        //调用豆瓣接口获取图书信息
        try {
            result = HttpClientUtil.getInstance().sendHttpGet(queryUrl);
            JSONObject jsonObj = JSONObject.parseObject(result);
            Object bookName = jsonObj.get("title");
            if (bookName != null) {
                logger.info("isbn query success!");
                //解析返回参数
                Book book = convertRes2Book(jsonObj.toJSONString());
                return book;
            } else {
                logger.info("isbn query fail!");
            }
        } catch (Exception e) {
            logger.info("isbn query exception!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将查询返回的数据转换成Book对象
     *
     * @param result
     *
     * @return
     */
    public Book convertRes2Book(String result) {
        Map<String, String> reqParam = (Map<String, String>) JSON.parse(result);
        Book book = new Book();
        //图书创建时间
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        book.setCreateTime(createTime);
        //图书名称
        String bookName = reqParam.get("title");
        book.setBookName(bookName);
        //isbn号
        String isbn = reqParam.get("isbn13");
        book.setIsbn(isbn);
        //封面
        String cover = reqParam.get("image");
        //小图换大图
        if (cover.contains("mpic")) {
            cover.replace("mpic", "lpic");
        }
        book.setCover(cover);
        //概要
        String summary = reqParam.get("summary");
        //概要信息防止过长，需要截取前200个字段
        if (summary.length() > Constants.SUMMARY_MAX_LENGTH) {
            summary = summary.substring(0, Constants.SUMMARY_MAX_LENGTH);
        }
        book.setSummary(summary);
        //出版社
        String publisher = reqParam.get("publisher");
        book.setPublisher(publisher);
        return book;
    }

    /**
     * 获取在借的图书
     *
     * @return
     */
    public List<BookInfoView> getOutBookList() {
        List<BookInfoView> bookDetailList = bookDetailDao.findOutBookList();
        return bookDetailList;
    }

    /**
     * 根据bookInfoList获取bookDetailList
     *
     * @param bookList
     *
     * @return
     */
    public List<BookDetail> getBookDetailListByBookInfo(List<BookInfoView> bookList) {
        List<BookDetail> bookDetailList = new ArrayList<BookDetail>();
        if (bookList == null || bookList.size() <= 0)
            return bookDetailList;
        for (BookInfoView bookInfo : bookList) {
            BookDetail bookDetail = convertBookInfo2Detail(bookInfo);
            bookDetailList.add(bookDetail);
        }
        return bookDetailList;
    }

    /**
     * 将bookInfo转化成bookDetail
     *
     * @param bookInfo
     *
     * @return
     */
    public BookDetail convertBookInfo2Detail(BookInfoView bookInfo) {
        BookDetail bookDetail = new BookDetail(bookInfo);
        BookBorrowInfo borrowInfo = new BookBorrowInfo("");
        bookDetail.setReaderId(borrowInfo.getReaderId());
        bookDetail.setReaderName(borrowInfo.getReaderName());
        bookDetail.setReaderTeam(borrowInfo.getReaderTeam());
        bookDetail.setEndTime(borrowInfo.getEndTime());
        return bookDetail;
    }

    /**
     * 分页搜索所有图书
     *
     * @param start
     * @param type
     *
     * @return
     */
    public List<BookInfoView> findBookListByLimits(Integer start, String type) {
        return bookDetailDao.findBookListByLimits(start, type);
    }

    /**
     * 获取用户拥有某一ISBN号的图书本数
     *
     * @param uid
     * @param isbn
     *
     * @return
     */
    public int getUserSameBookNums(String uid, String isbn) {
        List<Book> bookInfoList = bookDao.findUserBookNumsByIsbn(uid, isbn);
        if (bookInfoList == null) {
            return 0;
        }
        return bookInfoList.size();
    }

    /**
     * 获取用户借了某一ISBN号的图书本数
     *
     * @param uid
     * @param isbn
     * @param borrowHistoryList
     *
     * @return
     */
    public int getBorrowSameBookNums(String uid, String isbn, List<BorrowHistory> borrowHistoryList) {
        //获取在借的所有的图书id
        List<Integer> bookIdList = new ArrayList<Integer>();
        for (BorrowHistory borrowHistory : borrowHistoryList) {
            if (StringUtils.isBlank(borrowHistory.getReturnTime())) {
                bookIdList.add(Integer.valueOf(borrowHistory.getBookId()));
            }
        }
        //查询数据库，判断该isbn号出现了几次
        if (bookIdList.size() > 0) {
            List<Book> bookList = bookDao.findBorrowSameBookNums(isbn, bookIdList);
            return bookList.size();
        }
        return 0;
    }

    /**
     * 图书数量减1
     *
     * @param bookId
     */
    public void updateBookNums(String bookId) {
        bookDao.modifyBookNums(bookId);
    }
}
