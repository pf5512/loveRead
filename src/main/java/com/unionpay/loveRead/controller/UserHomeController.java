package com.unionpay.loveRead.controller;

import com.unionpay.loveRead.bean.BookDetail;
import com.unionpay.loveRead.bean.UserBorrowInfo;
import com.unionpay.loveRead.constants.AppConfig;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.domain.BookBorrowFlow;
import com.unionpay.loveRead.domain.BookInfoView;
import com.unionpay.loveRead.domain.ScoreFlow;
import com.unionpay.loveRead.service.*;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户类
 *
 * @author tony 2016年11月24日 下午10:50:15
 */
@Controller
@RequestMapping(value = "userHome")
public class UserHomeController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UserHomeController.class);

    @Autowired
    AppConfig appConfig;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    BorrowFlowService borrowFlowService;

    @Autowired
    BorrowService borrowService;

    @Autowired
    ScoreFlowService scoreFlowService;


    /**
     * 我的图书列表
     *
     * @param model
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "myBookList", method = RequestMethod.GET)
    public String myBookList(Model model, HttpServletRequest request) {
        logger.info("----------我的图书列表----------");
        //获取用户id
        String uid = getSessionUid(request);
        List<BookInfoView> bookList = bookService.getBooInfoListByUid(uid);
        List<BookDetail> bookDetailList = bookService.getBookDetailListByBookInfo(bookList);
        model.addAttribute("bookDetailList", bookDetailList);
        logger.info("----------我的图书个数:" + bookList.size() + "----------");
        return "userHome/myBook";
    }

    /**
     * 我的可借书列表
     *
     * @param model
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "myBorrowAbleList", method = RequestMethod.GET)
    public String borrowAbleList(Model model, HttpServletRequest request) {
        logger.info("----------我的可借图书列表----------");
        //获取用户id
        String uid = getSessionUid(request);
        String status = Constants.BOOK_STATUS_AVAILABLE;
        List<Book> bookList = bookService.getBookListByUid(uid, status);
        model.addAttribute("borrowAbleList", bookList);
        logger.info("----------我的可借图书个数:" + bookList.size() + "----------");
        return "userHome/availableList";
    }

    /**
     * 我的待还书列表
     *
     * @param model
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "myReturnList", method = RequestMethod.GET)
    public String myReturnList(Model model, HttpServletRequest request) {
        logger.info("----------我的待还图书列表----------");
        //获取用户id
        String uid = getSessionUid(request);
        List<BookBorrowFlow> historyList = borrowFlowService.getUserBorrowList(uid);
        logger.info("待还图书本数：" + historyList.size());
        List<UserBorrowInfo> returnList = borrowService.getBorrowIngBookList(historyList);
        model.addAttribute("returnList", returnList);
        logger.info("----------我的待还图书本数:" + returnList.size() + "----------");
        return "userHome/returnList";
    }

    /**
     * 我的借阅记录
     *
     * @param model
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "borrowHistory", method = RequestMethod.GET)
    public String borrowRecordList(Model model, HttpServletRequest request) {
        logger.info("----------查看我的借阅记录----------");
        //获取用户id
        String uid = getSessionUid(request);
        List<BookBorrowFlow> historyList = borrowFlowService.getUserBorrowList(uid);
        List<UserBorrowInfo> borrowList = borrowService.getBorrowHistory(historyList);
        model.addAttribute("borrowHistoryList", borrowList);
        logger.info("----------我的借阅记录数:" + borrowList.size() + "----------");
        return "userHome/borrowHistory";
    }

    /**
     * 我的积分
     *
     * @param model
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "scoreHistory", method = RequestMethod.GET)
    public String scoreHistory(Model model, HttpServletRequest request) {
        logger.info("----------查看我的积分记录----------");
        //获取用户id
        WxMpUser user = getUserInfo(request);
        String uid = user.getOpenId();
        //获取用户总积分
        model.addAttribute("score", "0");
        //获取用户积分流水
        List<ScoreFlow> scoreList = scoreFlowService.getAllByUid(uid);
        model.addAttribute("scoreList", scoreList);
        //增加抽奖消耗积分
        Integer lotteryCost = appConfig.getLotteryCost();
        model.addAttribute("lotteryCost", lotteryCost);
        return "userHome/scoreHistory";
    }

    /**
     * 某用户的书库
     *
     * @param model
     *
     * @return
     */
    @RequestMapping(value = "userBookList/{uid}")
    public String search(Model model, @PathVariable("uid") String uid) {
        //根据uid
        List<BookInfoView> bookList = bookService.getBooInfoListByUid(uid);
        model.addAttribute("bookDetailList", bookList);
        model.addAttribute("userName", userService.getUserByOpenId(uid).getNickName());
        return "userHome/userBookList";
    }

}
