package com.unionpay.loveRead.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/7/29 下午9:29  
 */
@Configuration
public class AppConfig {

    @Value("#{configuration.shareDir}")
    private String shareDir;

    @Value("#{configuration.app_url}")
    private String appUrl;

    @Value("#{configuration.same_book_limit}")
    private Integer sameBookLimit;

    @Value("#{configuration.user_book_limit}")
    private Integer userBookLimit;

    @Value("#{configuration.redis_ip}")
    private String redisIP;

    @Value("#{configuration.redis_port}")
    private String redisPort;

    @Value("#{configuration.douban_api_url}")
    private String doubanApiUrl;

    @Value("#{configuration.lottery_cost}")
    private Integer lotteryCost;

    @Value("#{configuration.tuling_api_url}")
    private String tulingApiUrl;

    @Value("#{configuration.tuling_api_key}")
    private String tulingApiKey;

    public String getAppUrl() {
        return appUrl;
    }

    public String getShareDir() {
        return shareDir;
    }

    public Integer getSameBookLimit() {
        return sameBookLimit;
    }

    public Integer getUserBookLimit() {
        return userBookLimit;
    }

    public String getRedisIP() {
        return redisIP;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public String getDoubanApiUrl() {
        return doubanApiUrl;
    }

    public Integer getLotteryCost() {
        return lotteryCost;
    }

    public String getTulingApiUrl() {
        return tulingApiUrl;
    }

    public String getTulingApiKey() {
        return tulingApiKey;
    }
}
