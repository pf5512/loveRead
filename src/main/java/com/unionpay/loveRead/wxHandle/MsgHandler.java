package com.unionpay.loveRead.wxHandle;

import com.unionpay.loveRead.service.SpdbcccRobot;
import com.unionpay.loveRead.service.TuLingRobot;
import com.unionpay.loveRead.service.WeixinService;
import com.unionpay.loveRead.wxMsgBuilder.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
public class MsgHandler extends AbstractHandler {
    @Autowired
    TuLingRobot tuLingRobot;

    @Autowired
    SpdbcccRobot spdbcccRobot;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

        WeixinService weixinService = (WeixinService) wxMpService;
        String content = "你说，反正我不回答你";

        if (wxMessage.getMsgType().equals(WxConsts.XML_MSG_TEXT)) {
            //如果是文本事件，则接入图灵机器人处理
            content = tuLingRobot.processMessage(wxMessage);
        }else if(wxMessage.getMsgType().equals(WxConsts.XML_MSG_LINK)){
            //如果是链接事件，则转浦发红包机器人处理
            if(wxMessage.getDescription().contains("红包")){
                content = spdbcccRobot.processMessage(wxMessage);
            }
        }else{
            content = "不好意思，我没法理解您在干嘛";
        }

        //组装回复消息
        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
