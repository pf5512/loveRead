package com.unionpay.loveRead.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.loveRead.constants.AppConfig;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.utils.HttpClientUtil;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/9/26 下午4:14  
 */
@Service
public class TuLingRobot {
    @Autowired
    AppConfig appConfig;
    private static Logger logger = LoggerFactory.getLogger(BookService.class);

    public String processMessage(WxMpXmlMessage wxMessage) {
        //1.调用图灵接口
        String fromUserName = wxMessage.getFromUser();
        String content = wxMessage.getContent();
        String info = "";
        try {
            info = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> paramMaps = new HashMap<>();
        paramMaps.put("key", appConfig.getTulingApiKey());
        paramMaps.put("info", info);
        paramMaps.put("userid", fromUserName);
        logger.info("paramMaps:"+paramMaps);
        String result = HttpClientUtil.getInstance().sendHttpPost(appConfig.getTulingApiUrl(),paramMaps);
        logger.info("result:"+result);
        //2.处理图灵机器人返回的结果
        String answer = processTuRingResult(result);
        return result;
    }

    /**
     * 处理图灵机器人返回的结果
     */
    public String processTuRingResult(String result){
        String answer = "";
        JSONObject rootObj = JSON.parseObject(result);
        String code = rootObj.getString("code");
        switch (code){
            case Constants.TEXT_CODE:
                answer = rootObj.getString("text");
                break;
            case Constants.LINK_CODE:
                answer = rootObj.getString("text");
                break;
            case Constants.NEWS_CODE:
            case Constants.TRAIN_CODE:
            case Constants.FLIGHT_CODE:
            case Constants.MENU_CODE:
                answer = "我暂时处理不了这些信息哦";
                break;
            case Constants.LENGTH_WRONG_CODE:
            case Constants.KEY_WRONG_CODE:
                answer = "我现在想一个人静一静,请等下再跟我聊天";
                break;
            case Constants.EMPTY_CONTENT_CODE:
                answer = "你不说话,我也没什么好说的";
                break;
            case Constants.NUMBER_DONE_CODE:
                answer = "我今天有点累了,明天再找我聊吧！";
                break;
            case Constants.NOT_SUPPORT_CODE:
                answer = "这个我还没学会,我以后会慢慢学的！";
                break;
            case Constants.UPGRADE_CODE:
                answer = "我经验值满了,正在升级中...";
                break;
            case Constants.DATA_EXCEPTION_CODE:
                answer = "你都干了些什么,我怎么话都说不清楚了";
                break;
            default:
                answer = "你说啥？";
                break;
        }

        return answer;
    }

    private void assembleNews(String answer, List<JSONObject> list, int code) {

    }
}
