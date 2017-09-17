package com.unionpay.loveRead.service;

import com.unionpay.loveRead.constants.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 启动并创建Jedis实例
 *
 * @author weidan
 */
@Service
public class RedisSingletonService {
    private static Logger logger = LoggerFactory.getLogger(RedisSingletonService.class);

    // Jedis连接池单例
    private static JedisPool redisPool = null;
    private static String redisIp;
    private static String redisPort;
    @Autowired
    private AppConfig appConfig;


    @PostConstruct
    public void init() {
        redisIp = appConfig.getRedisIP();
        redisPort = appConfig.getRedisPort();
    }

    /**
     * 创建Jedis单例
     *
     * @return
     */
    public static JedisPool getInstance() {
        if (redisPool == null) {
            synchronized (RedisSingletonService.class) {
                if (redisPool == null) {
                    redisPool = getPool();
                }
            }
        }
        return redisPool;
    }

    /**
     * 获取资源池
     *
     * @return
     */
    public static JedisPool getPool() {
        if (redisPool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(500);
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            // config.setMaxWait(1000 * 100);
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            redisPool = new JedisPool(config, redisIp,
                    Integer.parseInt(redisPort), 1000 * 100);
        }
        return redisPool;
    }

    /**
     * 判断redis中是否存在某个key
     *
     * @param key
     *
     * @return
     */
    public static boolean isExist(String key) {
        Jedis jedis = null;
        try {
            redisPool = getInstance(); // 获取资源池
            jedis = redisPool.getResource(); // 获取资源
            if (jedis != null) {
                return jedis.exists(key);
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.info("Jedis check isExist fail!");
            return false;
        } finally {
            if (jedis != null) {
                jedis.close(); // 释放连接
                logger.info("Jedis connection releases successfully!");
            }
        }
    }

    /**
     * 根据key获取value值
     *
     * @param key
     *
     * @return
     */
    public static String getKey(String key) {
        Jedis jedis = null;
        try {
            redisPool = getInstance(); // 获取资源池
            jedis = redisPool.getResource(); // 获取资源
            if (jedis != null) {
                return jedis.get(key);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.info("Jedis getKey fail!");
            return null;
        } finally {
            if (jedis != null) {
                jedis.close(); // 释放连接
                logger.info("Jedis connection releases successfully!");
            }
        }
    }

    /**
     * 存入key
     *
     * @param key
     *
     * @return
     */
    public static void setKey(String key, String value) {
        Jedis jedis = null;
        try {
            redisPool = getInstance(); // 获取资源池
            jedis = redisPool.getResource(); // 获取资源
            if (jedis != null) {
                jedis.set(key, value);
            }
        } catch (Exception e) {
            logger.info("Jedis setKey fail!");
        } finally {
            if (jedis != null) {
                jedis.close(); // 释放连接
                logger.info("Jedis connection releases successfully!");
            }
        }
    }

    /**
     * 增加元素至set
     *
     * @param setKey
     * @param value
     */
    public static void addSet(String setKey, String value) {
        Jedis jedis = null;
        try {
            redisPool = getInstance(); // 获取资源池
            jedis = redisPool.getResource(); // 获取资源
            if (jedis != null) {
                jedis.sadd(setKey, value);
            }
        } catch (Exception e) {
            logger.info("Jedis addSet fail!");
        } finally {
            if (jedis != null) {
                jedis.close(); // 释放连接
                logger.info("Jedis connection releases successfully!");
            }
        }
    }

    /**
     * 判断set是否存在某个元素
     *
     * @param setKey
     * @param setMember
     */
    public static boolean isExistInSet(String setKey, String setMember) {
        Jedis jedis = null;
        try {
            redisPool = getInstance(); // 获取资源池
            jedis = redisPool.getResource(); // 获取资源
            if (jedis != null) {
                return jedis.sismember(setKey, setMember);
            }
        } catch (Exception e) {
            logger.info("Jedis check set item fail!");
        } finally {
            if (jedis != null) {
                jedis.close(); // 释放连接
                logger.info("Jedis connection releases successfully!");
            }
        }
        return false;
    }

    /**
     * 从set中移除元素
     *
     * @param setKey
     * @param value
     */
    public static void remSet(String setKey, String value) {
        Jedis jedis = null;
        try {
            redisPool = getInstance(); // 获取资源池
            jedis = redisPool.getResource(); // 获取资源
            if (jedis != null) {
                jedis.srem(setKey, value);
            }
        } catch (Exception e) {
            logger.info("Jedis addSet fail!");
        } finally {
            if (jedis != null) {
                jedis.close(); // 释放连接
                logger.info("Jedis connection releases successfully!");
            }
        }
    }

    /**
     * 从set中获取某个元素
     *
     * @param setKey
     */
    public static Set<String> getSetMembers(String setKey) {
        Jedis jedis = null;
        try {
            redisPool = getInstance(); // 获取资源池
            jedis = redisPool.getResource(); // 获取资源
            if (jedis != null) {
                return jedis.smembers(setKey);
            }
        } catch (Exception e) {
            logger.info("Jedis addSet fail!");
        } finally {
            if (jedis != null) {
                jedis.close(); // 释放连接
                logger.info("Jedis connection releases successfully!");
            }
            return null;
        }
    }

    /**
     * 返回set集合中所有元素个数
     *
     * @param setKey
     */
    public static Long getTotalSetMembers(String setKey) {
        Jedis jedis = null;
        try {
            redisPool = getInstance(); // 获取资源池
            jedis = redisPool.getResource(); // 获取资源
            if (jedis != null) {
                return jedis.scard(setKey);
            }
        } catch (Exception e) {
            logger.info("Jedis count fail!");
        } finally {
            if (jedis != null) {
                jedis.close(); // 释放连接
                logger.info("Jedis connection releases successfully!");
            }
            return null;
        }
    }

    /**
     * 往redis里存储有时效性的值
     * @param key
     * @param value
     * @param seconds 0表示永不过期
     */
    public static boolean put(String key, String value, Integer seconds) {
        Jedis jedis = null;
        try {
            redisPool = getInstance();
            jedis = redisPool.getResource(); // 获取资源
            if (jedis != null) {
                String result = "";
                //如果是永久有效,
                if(seconds.equals(0)){
                    jedis.set(key,value);
                    return true;
                }else{
                    result = jedis.setex(key,seconds,value);
                    if(result.equalsIgnoreCase("ok")){
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.info("Jedis insert value fail!");
        } finally {
            if (jedis != null) {
                jedis.close(); // 释放连接
                logger.info("Jedis connection releases successfully!");
            }
        }
        return false;
    }
}
