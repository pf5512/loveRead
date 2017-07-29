package com.unionpay.loveRead.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/7/28 下午2:43  
 */
@Configuration
public class AppConfig {
    @Value("#{configuration.redis_ip}")
    private String redisIP;

    @Value("#{configuration.redis_port}")
    private String redisPort;

    public String getRedisIP() {
        return redisIP;
    }

    public String getRedisPort() {
        return redisPort;
    }
}
