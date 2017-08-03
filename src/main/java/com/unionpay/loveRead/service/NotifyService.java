package com.unionpay.loveRead.service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/8/3 下午5:12  
 */
@Service
public class NotifyService {
    private static Logger logger = LoggerFactory.getLogger(NotifyService.class);

    @Autowired
    WxMpService wxMpService;

    public void textNotify(String userId, String content) {

        WxMpKefuMessage ownerMessage = WxMpKefuMessage
                .TEXT()
                .toUser(userId)
                .content(content)
                .build();
        try {
            wxMpService.getKefuService().sendKefuMessage(ownerMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}
