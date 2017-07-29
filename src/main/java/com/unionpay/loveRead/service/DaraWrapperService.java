package com.unionpay.loveRead.service;

import com.unionpay.loveRead.constants.WxMpConfig;
import com.unionpay.loveRead.domain.WxUser;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/7/29 上午10:28  
 */
@Service
public class DaraWrapperService {

    @Autowired
    private WxMpConfig wxConfig;

    public WxUser convertWxUser(WxMpUser wxMpUser) {
        WxUser user = new WxUser();
        user.setAppId(wxConfig.getAppid());
        if(wxMpUser.getSubscribe()){
            user.setSubscribe(1);
        }
        user.setOpenId(wxMpUser.getOpenId());
        user.setNickName(wxMpUser.getNickname());
        user.setSex(wxMpUser.getSex());
        user.setCity(wxMpUser.getCity());
        user.setCountry(wxMpUser.getCountry());
        user.setProvince(wxMpUser.getProvince());
        user.setLanguage(wxMpUser.getLanguage());
        user.setHeadImgUrl(wxMpUser.getHeadImgUrl());
        user.setUnionId(wxMpUser.getUnionId());
        user.setRemark(wxMpUser.getRemark());
        user.setGroupId(wxMpUser.getGroupId());
        user.setSubscribeTime(new Timestamp(wxMpUser.getSubscribeTime()));
        return user;
    }
}
