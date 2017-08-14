package com.unionpay.loveRead.controller;

import com.unionpay.loveRead.bean.BaseResponse;
import com.unionpay.loveRead.bean.MomentsInfo;
import com.unionpay.loveRead.bean.RespStatus;
import com.unionpay.loveRead.constants.AppConfig;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.domain.Moments;
import com.unionpay.loveRead.domain.MomentsReply;
import com.unionpay.loveRead.domain.MomentsView;
import com.unionpay.loveRead.service.BookService;
import com.unionpay.loveRead.service.BorrowFlowService;
import com.unionpay.loveRead.service.DaraWrapperService;
import com.unionpay.loveRead.service.MomentsService;
import com.unionpay.loveRead.utils.CommonUtil;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
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

    @Autowired
    AppConfig appConfig;
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

    /**
     * 发表评论
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse add(HttpServletRequest request, String bookId,String content,
                            @RequestParam(value = "commentsImg", required = false) MultipartFile commentsImg) {
        logger.info("----------评论开始...");
        BaseResponse response = new BaseResponse();
        Moments moments = new Moments();
        MomentsInfo momentsInfo = new MomentsInfo();
        String userId = getSessionUid(request);
        moments.setUserId(userId);
        moments.setBookId(Integer.valueOf(bookId));
        moments.setMomentsContent(content);
        //保存图片
        if(commentsImg != null && !commentsImg.isEmpty()){
            String diskPath = appConfig.getShareDir() + File.separator;
            File targetFile = new File(diskPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            //获取文件后缀名
            String suffix = commentsImg.getOriginalFilename().substring(
                    commentsImg.getOriginalFilename().lastIndexOf("."));
            String imgPath = userId + File.separator + CommonUtil.getFileNameByDate() + suffix;
            targetFile = new File(diskPath + imgPath);
            try {
                commentsImg.transferTo(targetFile);
                moments.setMomentsImg(imgPath);
            } catch (IllegalStateException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        moments.setCrtTs(createTime);

        try{
            momentsService.addMoments(moments);
            momentsInfo = daraWrapperService.convertMoments2Info(moments);
            momentsInfo.setUserName("test");
            momentsInfo.setUserIcon("");
            response.setData(momentsInfo);
            logger.info(response.toString());
        }catch(Exception e){
            response.setCode(RespStatus.SERVER_ERROR.getCode());
            response.setMessage(RespStatus.SERVER_ERROR.getMessage());
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 回复评论
     * @param request
     * @return
     */
    @RequestMapping(value = "reply", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse reply(HttpServletRequest request,String momentsId,
                              String replyContent,String pReplyerId,String pReplyerName) {
        logger.info("----------回复评论...");
        BaseResponse response = new BaseResponse();
        MomentsReply momentsReply = new MomentsReply();
        WxMpUser user = getUserInfo(request);
        //赋值
        momentsReply.setMomentsId(momentsId);
        momentsReply.setReplyerId(user.getOpenId());
        momentsReply.setReplyerName(user.getNickname());
        momentsReply.setReplyContent(replyContent);
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        momentsReply.setReplyTime(createTime);
        momentsReply.setParentReplyerId(pReplyerId);
        momentsReply.setParentReplyerName(pReplyerName);

        try{
            momentsService.addMomentsReply(momentsReply);
            response.setData(momentsReply);
        }catch(Exception e){
            response.setCode(RespStatus.SERVER_ERROR.getCode());
            response.setMessage(RespStatus.SERVER_ERROR.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 删除评论
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public BaseResponse deleteReview(String momentsId,HttpServletRequest request) {
        logger.info("------用户删除评论操作-----");
        BaseResponse baseResp = new BaseResponse();
        String uid = getSessionUid(request);

        if(!StringUtils.isBlank(uid)){
            momentsService.deleteMoments(momentsId, uid);
        }else{
            baseResp.setCode(RespStatus.LOGIN_EXPIRED.getCode());
            baseResp.setMessage(RespStatus.LOGIN_EXPIRED.getMessage());
        }
        return baseResp;
    }

}
