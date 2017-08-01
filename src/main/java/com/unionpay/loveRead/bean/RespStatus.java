/*
 * 
 * 版权所有 <2015> <中国银联股份有限公司> 中国银联股份有限公司保留所有权利。
 * 该程序受《U聊开源许可证》的约束。
 * 
 */

package com.unionpay.loveRead.bean;

/**
 * @author leizhilong
 */
public enum RespStatus {

    OK("0", "成功"), // 操作成功
    FAIL("1", "失败"), // 操作失败，不满足业务逻辑
    LOGIN_EXPIRED("100", "登录已失效，请打开U聊重新登录"),
    LACK_PARAM("101", "缺少必要参数"),
    SERVER_ERROR("102", "服务器内部错误"), // 服务器异常了
    INVALID_REQ("103", "非法请求"), // 客户端请求有问题
    ANALYZE_FAIL("104", "解析失败"), // 解析失败
    NO_AUTH_BORROW("105", "您还不是我们的用户，请先进入首页，获得授权！"),//首次扫码用户信息为空
    SAME_BOOK_LIMIT("106", "您拥有的相同图书本数达到上限，请更换图书上传！"),
    BORROW_SAME_BOOK("107", "系统检测该书正在被您借阅，不可上传哦！"),
    SCORE_NOT_ENOUGH("108", "很抱歉，您的积分已不足！"),
    SCORE_NOT_ABLE("109", "很抱歉，今天的抽奖名额已结束，请明天再来！"),
    USER_BOOK_LIMIT("110", "好可惜，您拥有的图书总数达到上限了，不能在上传了哦！"),
    NOT_START("111", "活动尚未开始！"),
    ALREADY_END("112", "活动已经结束！"),
    JOINED("113", "很抱歉，您今日抽奖机会已全部用完，请明日再来！"),
    SEND_OVER("114", "很抱歉，图书已经赠送完毕！"),
    NOT_LUCKY_USER("115", "很抱歉，您没有获赠该书的资格哦！"),
    PRIZE_DAY_LIMIT("116", "很抱歉，今天的奖品已经发放完毕，请明天再来！"),
    USER_PRIZE_LIMIT("117", "您本期已经抽中过啦！下期再约哦~");

    private String code;
    private String message;
    private RespStatus(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.code;
    }

}
