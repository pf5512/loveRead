package com.unionpay.loveRead.service;

import com.unionpay.loveRead.bean.BookDetail;
import com.unionpay.loveRead.bean.BorrowHistory;
import com.unionpay.loveRead.bean.UserBorrowInfo;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.dao.BorrowCountDao;
import com.unionpay.loveRead.dao.BorrowFlowDao;
import com.unionpay.loveRead.domain.BookBorrowCount;
import com.unionpay.loveRead.domain.BookBorrowFlow;
import com.unionpay.loveRead.domain.BookInfoView;
import com.unionpay.loveRead.utils.MyDateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BorrowService {

    private static Logger logger = LoggerFactory.getLogger(BorrowService.class);

    @Autowired
    BorrowFlowDao borrowFlowDao;


    @Autowired
    BorrowCountDao borrowCountDao;

    @Autowired
    BookService bookService;

    /**
     * 增加借阅记录
     *
     * @param readerId
     * @param bookId
     * @param lastDay
     */
    public void addBorrowRecord(String readerId, String bookId, Integer lastDay) {
        BookBorrowFlow borrow = new BookBorrowFlow();
        //借阅者id
        borrow.setReaderId(readerId);
        //图书id
        int bookIdInt = Integer.valueOf(bookId);
        borrow.setBookId(bookIdInt);
        //出借时间
        Timestamp outTime = new Timestamp(System.currentTimeMillis());
        borrow.setOutTime(outTime);
        //到期时间
        Timestamp endTime = MyDateUtil.getEndTime(outTime, lastDay);
        borrow.setEndTime(endTime);
        //借阅状态
        borrow.setStatus(Constants.BORROW_ING);
        borrowFlowDao.save(borrow);
    }

    /**
     * 更新借阅记录-还书
     *
     * @param readerId
     * @param bookId
     */
    public void updateBorrowRecord(String readerId, String bookId, Timestamp backTime) {
        //查询该用户在借的借阅记录
        BookBorrowFlow borrowRecord = getBorrowRecordById(readerId, bookId, Constants.BORROW_ING);
        if (borrowRecord != null) {
            if (backTime.compareTo(borrowRecord.getEndTime()) > 0) {
                borrowRecord.setStatus(Constants.BORROW_OVERDUE);
            } else {
                borrowRecord.setStatus(Constants.BORROW_BACK);
            }
            borrowRecord.setBackTime(backTime);
            borrowFlowDao.modify(borrowRecord);
        }
    }

    /**
     * 查询用户某本书的借阅记录
     *
     * @param readerId
     * @param bookId
     *
     * @return
     */
    public BookBorrowFlow getBorrowRecordById(String readerId, String bookId, String status) {
        int bookIdInt = Integer.valueOf(bookId);
        return borrowFlowDao.findRecordById(readerId, bookIdInt, status);
    }

    /**
     * 根据uid和借阅状态，获取用户的借阅详情
     *
     * @param uid
     * @param borrowStatus null时表示查询该用户全部的借阅记录
     *
     * @return
     */
    public List<BookDetail> getBorrowRecord(String uid, String borrowStatus) {

        return null;
    }

    /**
     * 事务性操作-借书
     *
     * @param bookId
     * @param readerId
     * @param lastDay
     *
     * @return
     */
    public boolean borrowBook(String bookId, String readerId, String ownerId, Integer lastDay) {
        logger.info("----------开始借阅...");
        boolean isSuccess = true;
        try {
            //更新图书状态-借出
            bookService.updateBookStatus(bookId, Constants.BOOK_STATUS_OUT);
            logger.info("-----更新图书状态结束-----");
            //借阅流水表加入一条借阅流水
            addBorrowRecord(readerId, bookId, lastDay);
            logger.info("-----增加借阅流水结束-----");
            //更新借阅总数表，先判断总数表是否存在该书的记录，存在则增加借阅数，否则新增记录
            int bookIdInt = Integer.valueOf(bookId);
            BookBorrowCount borrowCount = borrowCountDao.get(bookIdInt);
            if (borrowCount != null) {
                borrowCountDao.addBookBorrowCounts(bookIdInt);
            } else {
                borrowCount = new BookBorrowCount();
                borrowCount.setBookId(bookIdInt);
                borrowCount.setBorrowNums(1);
                borrowCountDao.save(borrowCount);
            }
            logger.info("-----更新借阅总数结束-----");
        } catch (Exception e) {
            isSuccess = false;
            logger.info("借阅失败!");
        }
        return isSuccess;
    }

    /**
     * 事务性操作-还书
     *
     * @param bookId
     * @param readerId
     * @param ownerId
     *
     * @return
     */
    public boolean returnBook(String bookId, String readerId, String ownerId) {
        logger.info("----------开始还书...");
        boolean isSuccess = true;
        try {
            //更新图书状态--可借
            bookService.updateBookStatus(bookId, Constants.BOOK_STATUS_AVAILABLE);
            logger.info("-----更新图书状态结束-----");
            //更新借阅流水表
            Timestamp backTime = new Timestamp(System.currentTimeMillis());
            updateBorrowRecord(readerId, bookId, backTime);
            logger.info("-----更新借阅流水结束-----");
            //TODO 还书增减积分

        } catch (Exception e) {
            isSuccess = false;
            logger.info("还书失败！");
        }
        return isSuccess;
    }

    /**
     * 根据查询结果获取用户借阅记录
     *
     * @param historyList
     *
     * @return
     */
    public List<UserBorrowInfo> getBorrowHistory(List<BorrowHistory> historyList) {
        List<UserBorrowInfo> userBorrowList = new ArrayList<UserBorrowInfo>();
        for (BorrowHistory borrowHistory : historyList) {
            UserBorrowInfo userBorrowInfo = getUserBorrowInfo(borrowHistory);
            userBorrowList.add(userBorrowInfo);
        }
        return userBorrowList;
    }

    /**
     * 获取用户单本书的借阅信息
     *
     * @param borrowHistory
     *
     * @return
     */
    private UserBorrowInfo getUserBorrowInfo(BorrowHistory borrowHistory) {
        UserBorrowInfo userBorrowInfo = new UserBorrowInfo();
        BookInfoView bookInfo = bookService.getBookDetailByBookId(borrowHistory.getBookId());
        userBorrowInfo.setBookId(borrowHistory.getBookId());
        userBorrowInfo.setOwnerId(bookInfo.getOwnerId());
        userBorrowInfo.setBookName(bookInfo.getBookName());
        //没有归还时间则表示在借
        if (StringUtils.isBlank(borrowHistory.getReturnTime())) {
            userBorrowInfo.setBookStatus(Constants.BORROW_ING);
        } else {
            userBorrowInfo.setBookStatus(Constants.BORROW_BACK);
        }
        userBorrowInfo.setCover(bookInfo.getCover());
        userBorrowInfo.setOwnerName(bookInfo.getOwnerName());
        //计算到期时间
        String outTimeStr = borrowHistory.getReadTime();
        Timestamp outTime = MyDateUtil.str2Timestamp(outTimeStr);
        Timestamp endTime = MyDateUtil.getEndTime(outTime, Constants.LASTDAY);
        if (!StringUtils.isBlank(borrowHistory.getReturnTime())) {
            Timestamp backTime = MyDateUtil.str2Timestamp(borrowHistory.getReturnTime());
            userBorrowInfo.setBackTime(backTime);
        }
        userBorrowInfo.setEndTime(endTime);
        userBorrowInfo.setOutTime(outTime);
        return userBorrowInfo;
    }

    /**
     * 获取用户在借（可归还）的图书
     *
     * @param historyList
     *
     * @return
     */
    public List<UserBorrowInfo> getBorrowIngBookList(List<BorrowHistory> historyList) {
        List<UserBorrowInfo> userBorrowList = new ArrayList<UserBorrowInfo>();
        for (BorrowHistory borrowHistory : historyList) {
            //没有归还时间则表示在借
            if (StringUtils.isBlank(borrowHistory.getReturnTime())) {
                UserBorrowInfo userBorrowInfo = getUserBorrowInfo(borrowHistory);
                userBorrowList.add(userBorrowInfo);
            }
        }
        return userBorrowList;
    }
}
