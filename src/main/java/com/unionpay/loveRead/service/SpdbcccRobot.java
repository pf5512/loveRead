package com.unionpay.loveRead.service;

import com.unionpay.loveRead.constants.AppConfig;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/9/26 下午4:18  
 */
@Service
public class SpdbcccRobot {
    @Autowired
    AppConfig appConfig;

    public String processMessage(WxMpXmlMessage wxMessage) {
        return "我已收到你发的红包";
    }
}
