package com.unionpay.loveRead.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/7/28 下午3:24  
 */
public class WxMenuKey {
    private String type;
    private String content;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public WxMenuKey() {

    }

    public WxMenuKey(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
