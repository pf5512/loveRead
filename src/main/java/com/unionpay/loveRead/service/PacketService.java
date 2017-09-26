package com.unionpay.loveRead.service;

import com.unionpay.loveRead.dao.StorePacketDao;
import com.unionpay.loveRead.domain.StorePacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/9/26 下午8:20  
 */
@Service
public class PacketService {
    @Autowired
    private StorePacketDao storePacketDao;

    /**
     * 保存用户发送给机器人的红包
     * @param fromUserName
     * @param packetUrl
     * @param packetId
     * @return
     */
    public boolean storeNewPacket(String fromUserName, String packetUrl,
                                  String packetId) {
        StorePacket storePacket = new StorePacket();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        storePacket.setPacketId(packetId);
        storePacket.setUserId(fromUserName);
        storePacket.setPacketUrl(packetUrl);
        storePacket.setCreateTime(now);
        try{
            storePacketDao.save(storePacket);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
