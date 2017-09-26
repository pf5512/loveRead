package com.unionpay.loveRead.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/9/26 下午7:39  
 */
public class Test {
    public static void main(String[] args){
        String packetContent = "https://weixin.spdbccc.com.cn/wxrp-page-redpacketsharepage/share?packetId=OY00MTWOUX6FQ6Z1494342435-1506213317000b05adb07&noCheck=1&hash=89";
        System.out.println(getHash(packetContent));
        System.out.println(getPacketId(packetContent));
    }
    /*
     * 获取hash值
     */
    private static String getHash(String packetContent) {
        Pattern hashPattern = Pattern.compile("hash\\=([^<]*)");
        Matcher hashMatcher = hashPattern.matcher(packetContent);
        if(hashMatcher.find()){
            return hashMatcher.group(1);
        }
        return null;
    }

    /*
     * 获取红包Id
     */
    private static String getPacketId(String packetContent) {
        Pattern packetIdPattern = Pattern.compile("packetId\\=([^&]*)");
        Matcher packetIdMatcher = packetIdPattern.matcher(packetContent);
        if(packetIdMatcher.find()){
            return packetIdMatcher.group(1);
        }
        return null;
    }
}
