package com.unionpay.loveRead.controller;

import com.unionpay.loveRead.bean.MomentsInfo;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.domain.MomentsView;
import com.unionpay.loveRead.service.BookService;
import com.unionpay.loveRead.service.BorrowFlowService;
import com.unionpay.loveRead.service.DaraWrapperService;
import com.unionpay.loveRead.service.MomentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/8/4 下午5:04  
 */
@Controller
@RequestMapping(value = "moments")
public class MomentsController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(MomentsController.class);

    @Autowired
    MomentsService momentsService;

    @Autowired
    DaraWrapperService daraWrapperService;

    @Autowired
    BookService bookService;

    @Autowired
    BorrowFlowService borrowFlowService;
    /**
     * 圈子首页
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "list")
    public String list(Model model,HttpServletRequest request,
                       HttpServletResponse response){
        String userId = getSessionUid(request);
        //从视图中查出所有的发表信息和对应的回复信息
        List<MomentsView> momentViewList = momentsService.getMomentsViewList(0);
        //将回复信息归类到每条评论下
        List<MomentsInfo> momentsInfoList = daraWrapperService.convertView2Info(momentViewList);
        model.addAttribute("momentsInfoList", momentsInfoList);
        model.addAttribute("currentUserId", userId);

        //获取我发布的消息列表
        List<MomentsInfo> myMomentsList = momentsService.getUserMomentsList(userId, momentsInfoList);
        model.addAttribute("myMomentsList", myMomentsList);
        logger.info("--------我共" + myMomentsList.size() + "条消息---------");

        //获取用户可评论的图书列表(目前拥有和曾经借过的图书)
        List<Book> ownBookList = bookService.getBookListByUid(userId, Constants.BOOK_STATUS_AVAILABLE);
        List<Book> historyList = borrowFlowService.getBorrowedBookList(userId);
        List<Book> reviewBookList = momentsService.getReviewBookList(ownBookList, historyList);
        model.addAttribute("reviewBookList", reviewBookList);
        return "review/review";
    }

}
