package com.unionpay.loveRead.service;

import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.dao.UserDao;
import com.unionpay.loveRead.domain.WxUser;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    DaraWrapperService daraWrapperService;

    /**
     * 获取用户信息
     *
     * @param openId
     *
     * @return
     */
    public WxUser getUserByOpenId(String openId) {
        return userDao.findUser(openId);
    }

    /**
     * 新增用户
     *
     * @param wxMpUser
     */
    public void addUser(WxMpUser wxMpUser) {
        if (wxMpUser != null) {
            WxUser wxUser = daraWrapperService.convertWxUser(wxMpUser);
            userDao.save(wxUser);
            //将用户信息放入缓存
            RedisSingletonService.addSet(Constants.REDIS_USERSET_KEY, wxUser.getOpenId());
        } else {
            logger.info("新增用户失败：微信用户信息为空！");
        }
    }

    public void updateUser(WxUser user) {
        userDao.saveOrUpdate(user);
    }

    /**
     * 根据积分获取用户排行
     *
     * @param num
     *
     * @return
     */
    public List<WxUser> getUserRankByScore(String num) {
        int numInt = 0;
        if (num != null) {
            numInt = Integer.valueOf(num);
        }
        List<WxUser> userList = userDao.findUserListOrderByScore(numInt);
        return userList;
    }

    /**
     * 分页搜索所有用户
     *
     * @param start
     *
     * @return
     */
    public List<WxUser> findUserListByLimits(Integer start) {
        return userDao.findUserListByLimits(start);
    }

}
