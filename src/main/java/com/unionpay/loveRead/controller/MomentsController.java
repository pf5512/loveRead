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
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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

    @Autowired
    private WxMpService wxMpService;
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
        List<MomentsInfo> momentsInfoList = daraWrapperService.convertView2Info(momentViewList,userId);
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

        //获取js api
        try {
            String url = processUrl(request, appConfig.getAppUrl());
            logger.info("url:" + url);
            WxJsapiSignature wxConfig = wxMpService.createJsapiSignature(url);
            model.addAttribute("wxConfig", wxConfig);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return "review/review";
    }

    /**
     * 发布消息
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse add(HttpServletRequest request, String bookId,String content,String commentsImg) {
        logger.info("----------发布消息...");
        BaseResponse response = new BaseResponse();
        Moments moments = new Moments();
        MomentsInfo momentsInfo;
        WxMpUser user = getUserInfo(request);
        String userId = user.getOpenId();
//        String userId = getSessionUid(request);
        moments.setUserId(userId);
        moments.setBookId(Integer.valueOf(bookId));
        moments.setMomentsContent(content);
        //保存图片
        if(!StringUtils.isBlank(commentsImg)){
            logger.info("图片不为空！"+commentsImg);
            moments.setMomentsImg(commentsImg);
        }

        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        moments.setCrtTs(createTime);
        try{
            momentsService.addMoments(moments);
            momentsInfo = daraWrapperService.convertMoments2Info(moments);
            momentsInfo.setUserName(user.getNickname());
            momentsInfo.setUserIcon(user.getHeadImgUrl());
            response.setData(momentsInfo);
        }catch(Exception e){
            response.setCode(RespStatus.SERVER_ERROR.getCode());
            response.setMessage(RespStatus.SERVER_ERROR.getMessage());
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 回复消息
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

    /**
     * 保存消息图片至服务器
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "savePicture")
    @ResponseBody
    public BaseResponse savePicture(String mediaId,HttpServletRequest request) {
        logger.info("------保存用户消息图片-----");
        BaseResponse baseResp = new BaseResponse();
        String uid = getSessionUid(request);
        // 获得一个在系统临时目录的文件
        try {
            File file = wxMpService.getMaterialService().mediaDownload(mediaId);
            String fileName = file.getName();
            logger.info("fileName:"+fileName);
            baseResp.setMessage(fileName);
        } catch (WxErrorException e) {
            baseResp.setCode(RespStatus.FAIL.getCode());
            baseResp.setMessage("保存失败，请重新上传！");
            e.printStackTrace();
        }

        return baseResp;
    }

    /**
     * 用户点/取赞
     *
     * @param momentsId
     * @param request
     * @return
     */
    @RequestMapping(value = "addLike")
    @ResponseBody
    public BaseResponse addLike(String momentsId,HttpServletRequest request) {
        logger.info("------用户对状态点赞操作-----");
        BaseResponse baseResp = new BaseResponse();
        WxMpUser user = getUserInfo(request);

        if(!StringUtils.isBlank(user.getOpenId())){
            String result = momentsService.addLike(user.getOpenId(), momentsId);
            baseResp.setCode(result);
        }else{
            baseResp.setCode(RespStatus.LOGIN_EXPIRED.getCode());
            baseResp.setMessage(RespStatus.LOGIN_EXPIRED.getMessage());
        }

        return baseResp;
    }

}
