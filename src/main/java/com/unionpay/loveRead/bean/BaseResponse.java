/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unionpay.loveRead.bean;

/**
 * 接口返回值基础类.
 *
 * @author tony 2016年11月24日 下午10:49:50
 */
public class BaseResponse {
    /**
     * 操作成功返回{@code 0}.
     * 其他参见RespStatus
     */
    private String code = RespStatus.OK.getCode();
    /**
     * 用户客户端提示的信息.
     */
    private String message = RespStatus.OK.getMessage();
    /**
     * 接口返回的对象.
     */
    private Object data;

    /**
     * 操作成功返回{@code 0}.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * 操作成功返回{@code 0}.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 用户客户端提示的信息.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 用户客户端提示的信息.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 接口返回的对象.
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * 接口返回的对象.
     *
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "code:" + code + ";message:" + message + ";data:" + data;
    }
}
