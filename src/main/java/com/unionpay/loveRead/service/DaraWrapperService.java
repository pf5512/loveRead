package com.unionpay.loveRead.service;

import com.unionpay.loveRead.constants.WxMpConfig;
import com.unionpay.loveRead.domain.WxUser;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang.StringUtils;
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
}
