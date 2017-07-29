package com.unionpay.loveRead.dao;

import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.WxUser;
import com.unionpay.loveRead.plugins.HibernateBaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class UserDao extends HibernateBaseDao<WxUser, Serializable> {
    
    /**
     * 根据openId获取用户信息
     * @param openId
     * @return
     */
    public WxUser findUser(String openId){
    	String hql ="from WxUser where openId = ?";
    	List<WxUser> userList = find(hql,openId);
        if(userList.size()>0){
            return userList.get(0);
        }
        return null;
    }
    
    public String findUserIdByOpenId(String openId){
    	String hql = "from WxUser where openId = ?";
    	List<WxUser> userList = find(hql,openId);
        if(userList.size()>0){
            return userList.get(0).getOpenId();
        }
        return null;
    }

    /**
     * 根据积分获取用户排行
     * @param numInt
     * @return
     */
    public List<WxUser> findUserListOrderByScore(int numInt) {
        String hql = "from WxUser order by score desc ";
        if (numInt > 0) {
            return findPage(hql, 0, numInt);
        }
        return find(hql);
    }
    
    /**
     *分页查询所有用户 
     * @param start
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<WxUser> findUserListByLimits(Integer start) {
		String hql = "from WxUser where 1=1 ";
		Query query = null;
		if(start!=null){
			query = this.createQuery(hql).setFirstResult(start).setMaxResults(Constants.PAGE_SIZE);
		}else{
			query = this.createQuery(hql);
		}
		return query.list();
	}

    /**
     * 根据uid列表查询用户信息
     * @param uidList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<WxUser> findUserListByUidList(List<String> uidList) {
        String hql = "from WxUser where openId in :uidList order by score desc";
        // 组合参数值
        Query query = createQuery(hql);
        query.setParameterList("uidList", uidList);
        return query.list();
    }

}
