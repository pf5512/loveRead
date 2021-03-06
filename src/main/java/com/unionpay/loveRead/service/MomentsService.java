package com.unionpay.loveRead.service;

import com.unionpay.loveRead.bean.MomentsInfo;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.dao.MomentsDao;
import com.unionpay.loveRead.dao.MomentsReplyDao;
import com.unionpay.loveRead.dao.MomentsViewDao;
import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.domain.Moments;
import com.unionpay.loveRead.domain.MomentsReply;
import com.unionpay.loveRead.domain.MomentsView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/8/9 下午9:04  
 */
@Service
@Transactional
public class MomentsService {
    private static Logger logger = LoggerFactory.getLogger(BorrowService.class);

    @Autowired
    MomentsViewDao momentsViewDao;

    @Autowired
    MomentsDao momentsDao;

    @Autowired
    MomentsReplyDao momentsReplyDao;

    /**
     * 获取圈子首页状态信息
     * @param start
     * @return
     */
    public List<MomentsView> getMomentsViewList(Integer start) {
        List<MomentsView> momentsViewList = momentsViewDao.findNormalMomentsViewList();
        return momentsViewList;
    }

    /**
     * 获取用户发布的消息列表
     * @param userId
     * @param momentsInfosList
     * @return
     */
    public List<MomentsInfo> getUserMomentsList(String userId, List<MomentsInfo> momentsInfosList) {
        List<MomentsInfo> myReviewList = new ArrayList<MomentsInfo>();
        for(MomentsInfo momentsInfo : momentsInfosList){
            if(momentsInfo.getUserId().equals(userId)){
                myReviewList.add(momentsInfo);
            }
        }
        return myReviewList;
    }

    /**
     * 获取用户可以评论的图书列表
     * @param ownBookList
     * @param historyList
     * @return
     */
    public List<Book> getReviewBookList(List<Book> ownBookList, List<Book> historyList) {
        Map<Integer,String> bookIdMap = new HashMap<>();
        if(ownBookList ==null || ownBookList.size() < 1){
            return historyList;
        }else if(historyList ==null || historyList.size() < 1){
            return ownBookList;
        }else{
            //定义一个bookIdMap,key为bookId,value无所谓,去重
            for(Book ownBook : ownBookList){
                bookIdMap.put(ownBook.getId(),"");
            }
            for(Book historyBook : historyList){
                //图书去重。已拥有的图书id不包含借阅过的图书id时，追加到可评论的图书列表中
                if(!bookIdMap.containsKey(historyBook.getId())){
                    ownBookList.add(historyBook);
                }
            }
            return ownBookList;
        }
    }

    /**
     * 新增消息
     * @param moments
     */
    public void addMoments(Moments moments) {
        momentsDao.save(moments);
    }

    /**
     * 新增评论
     * @param momentsReply
     */
    public void addMomentsReply(MomentsReply momentsReply) {
        momentsReplyDao.save(momentsReply);
    }

    /**
     * 删除消息
     * @param momentsId
     * @param uid
     */
    public void deleteMoments(String momentsId, String uid) {
        momentsDao.updateMomentsStatus(momentsId);
    }

    /**
     * 点赞/取赞
     * @param uid
     * @param momentsId
     * @return
     */
    public String addLike(String uid, String momentsId) {
        String result = Constants.FAIL;
        //点赞key
        String momentsLikeKey = Constants.REDIS_KEY_PRFIX_MOMENT_LIKE
                + Constants.REDIS_KEY_AND_FLAG + momentsId;
        try{
            //如果redis存在该key，表示取消赞，否则是点赞
            if(RedisSingletonService.isExistInSet(momentsLikeKey,uid)){
                RedisSingletonService.remSet(momentsLikeKey, uid);
                result = Constants.SUCCESS_DOWN;
            }else{
                RedisSingletonService.addSet(momentsLikeKey, uid);
                result = Constants.SUCCESS_UP;
            }
        }catch (Exception e){
            result = Constants.FAIL;
        }
        return result;
    }
}
