package com.unionpay.loveRead.service;

import com.unionpay.loveRead.bean.MomentsInfo;
import com.unionpay.loveRead.constants.WxMpConfig;
import com.unionpay.loveRead.domain.MomentViewId;
import com.unionpay.loveRead.domain.MomentsReply;
import com.unionpay.loveRead.domain.MomentsView;
import com.unionpay.loveRead.domain.WxUser;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/7/29 上午10:28  
 */
@Service
public class DaraWrapperService {

    @Autowired
    private WxMpConfig wxConfig;

    /**
     * 将微信返回的用户信息封装成系统需要的用户信息
     * @param wxMpUser
     * @return
     */
    public WxUser convertWxUser(WxMpUser wxMpUser) {
        WxUser user = new WxUser();
        user.setAppId(wxConfig.getAppid());
        if (wxMpUser.getSubscribe() != null
                && wxMpUser.getSubscribe()) {
            user.setSubscribe(1);
        }
        user.setOpenId(wxMpUser.getOpenId());
        user.setNickName(wxMpUser.getNickname());
        user.setSex(wxMpUser.getSex());
        user.setCity(wxMpUser.getCity());
        user.setCountry(wxMpUser.getCountry());
        user.setProvince(wxMpUser.getProvince());
        user.setLanguage(wxMpUser.getLanguage());
        if (!StringUtils.isEmpty(wxMpUser.getHeadImgUrl())
                && wxMpUser.getHeadImgUrl().contains("http")) {
            user.setHeadImgUrl(wxMpUser.getHeadImgUrl());
        }
        user.setUnionId(wxMpUser.getUnionId());
        if (!StringUtils.isEmpty(wxMpUser.getRemark())) {
            user.setRemark(wxMpUser.getRemark());
        }
        if (wxMpUser.getGroupId() != null) {
            user.setGroupId(wxMpUser.getGroupId());
        }
        if (wxMpUser.getSubscribeTime() != null) {
            user.setSubscribeTime(new Timestamp(wxMpUser.getSubscribeTime()));
        }
        return user;
    }

    public List<MomentsInfo> convertView2Info(List<MomentsView> momentViewList) {
        List<MomentsInfo> momentsInfoList = new ArrayList<>();
        //遍历所有评论和回复信息，并将review归类，放入map中
        LinkedHashMap<Integer,MomentsInfo> momentsInfoMap = new LinkedHashMap<Integer,MomentsInfo>();
        List<MomentsReply>  momentsReplyList;
        MomentsInfo momentsInfo;
        MomentsReply momentsReply;
        //遍历视图，将所有评论和回复信息分别归类到对应的消息下
        for(int i = 0 ; i < momentViewList.size() ; i++) {
            MomentsView momentsView = momentViewList.get(i);
            //视图主键
            MomentViewId momentViewId = momentsView.getMomentViewId();
            //为消息添加回复和评论信息
            momentsReply = new MomentsReply();
            momentsReply.setReplyerId(momentsView.getReplyerId());
            momentsReply.setReplyerName(momentsView.getReplyerName());
            momentsReply.setParentReplyerId(momentsView.getParentReplyerId());
            momentsReply.setParentReplyerName(momentsView.getParentReplyerName());
            momentsReply.setReplyContent(momentsView.getReplyContent());

            //如果该条消息已经在map中记录过，则向该消息的回复列表中追加新的评论信息
            if(momentsInfoMap.containsKey(momentViewId.getMomentId())){
                momentsInfo = momentsInfoMap.get(momentViewId.getMomentId());
                momentsReplyList = momentsInfo.getMomentsReplyList();
                momentsReplyList.add(momentsReply);
                momentsInfo.setMomentsReplyList(momentsReplyList);
            }else{
                //如果是新的消息，则定义一个消息对象
                momentsInfo = new MomentsInfo();
                momentsInfo.setMomentId(momentViewId.getMomentId());
                momentsInfo.setUserId(momentsView.getUserId());
                momentsInfo.setUserName(momentsView.getUserName());
                momentsInfo.setUserIcon(momentsView.getUserIcon());
                momentsInfo.setCrtTs(momentsView.getCrtTs());
                momentsInfo.setContent(momentsView.getMomentContent());
                momentsInfo.setMomentsImg(momentsView.getMomentImg());
                momentsInfo.setBookId(momentsView.getBookId());
                momentsInfo.setBookName(momentsView.getBookName());
                //同时定义该评论的回复列表
                momentsReplyList = new ArrayList<MomentsReply>();
                momentsReplyList.add(momentsReply);
                //将回复列表加入评论中
                momentsInfo.setMomentsReplyList(momentsReplyList);
                //存入map
                momentsInfoMap.put(momentViewId.getMomentId(), momentsInfo);
            }
        }
        //遍历LinkedHashMap,并添加点赞信息
        Iterator<Map.Entry<Integer,MomentsInfo>> iterator= momentsInfoMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer,MomentsInfo> entry = iterator.next();
            MomentsInfo review =  entry.getValue();
            momentsInfoList.add(review);
        }
        return momentsInfoList;
    }
}
