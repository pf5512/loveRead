package com.unionpay.loveRead.service;

import com.unionpay.loveRead.constants.AppConfig;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.utils.HttpClientUtil;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/9/26 下午4:18  
 */
@Service
public class SpdbcccRobot {
    private static Logger logger = LoggerFactory.getLogger(TuLingRobot.class);

    @Autowired
    AppConfig appConfig;

    @Autowired
    PacketService packetService;

    public String processMessage(WxMpXmlMessage wxMessage) {
        //1.空包检测
        String packetUrl = wxMessage.getUrl();
        if(StringUtils.isBlank(packetUrl)){
            return Constants.EMPTY_PACKET_TIPS;
        }

        String packetId = getPacketId(packetUrl);
        String hash = getHash(packetUrl);
        if(StringUtils.isBlank(packetId) || StringUtils.isBlank(hash)){
            return Constants.EMPTY_PACKET_TIPS;
        }
        //2.重复的红包检测
        String fromUserName = wxMessage.getFromUser();


        //3.无效的红包检测
        Map<String, String> paramMaps = new HashMap<>();
        paramMaps.put("packetId", packetId);
        paramMaps.put("hash", hash);
        String resp = HttpClientUtil.getInstance().sendHttpGet(appConfig.getSpdbcccUrl(), paramMaps);
        logger.info("resp:"+resp);
        if(!StringUtils.isBlank(resp)){
            if(resp.indexOf("onclick=\"submitInfo()") != -1){
                //3.存储1个红包
                boolean storeSuccess = packetService.storeNewPacket(fromUserName, packetUrl, packetId);
                if(storeSuccess){
                    //4.TODO 分配5个红包

                    return "我已收到你发的红包,但数据库暂无其他人的红包";
                }else{
                    logger.info("-----"+fromUserName+"的红包无法保存-----");
                }
            }else{
                return Constants.INVALID_PACKET_TIPS;
            }
        }
        return Constants.RESEND_PACKET_TIPS;
    }

    /*
     * 获取hash值
     */
    private String getHash(String packetUrl) {
        Pattern hashPattern = Pattern.compile("hash\\=([^<]*)");
        Matcher hashMatcher = hashPattern.matcher(packetUrl);
        if(hashMatcher.find()){
            return hashMatcher.group(1);
        }
        return null;
    }

    /*
     * 获取红包Id
     */
    private String getPacketId(String packetUrl) {
        Pattern packetIdPattern = Pattern.compile("packetId\\=([^&]*)");
        Matcher packetIdMatcher = packetIdPattern.matcher(packetUrl);
        if(packetIdMatcher.find()){
            return packetIdMatcher.group(1);
        }
        return null;
    }

}
