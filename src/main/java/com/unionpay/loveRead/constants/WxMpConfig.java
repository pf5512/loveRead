package com.unionpay.loveRead.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/7/28 下午2:43  
 */
@Configuration
public class WxMpConfig {
    @Value("#{configuration.wx_token}")
    private String token;

    @Value("#{configuration.wx_appid}")
    private String appid;

    @Value("#{configuration.wx_appsecret}")
    private String appsecret;

    @Value("#{configuration.wx_aeskey}")
    private String aesKey;

    @Value("#{configuration.shareDir}")
    private String tmpDirFile;

    public String getToken() {
        return this.token;
    }

    public String getAppid() {
        return this.appid;
    }

    public String getAppsecret() {
        return this.appsecret;
    }

    public String getAesKey() {
        return this.aesKey;
    }

    public File getTmpDirFile() {
        File tempFileDir = new File(this.tmpDirFile);
        return tempFileDir;
    }
}
