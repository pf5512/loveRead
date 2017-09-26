package com.unionpay.loveRead.constants;


/**
 * 常量类
 *
 * @author huxiaozhou
 */
public class Constants {

    public static final String API_KEY = "137b97f1f2c702d4551887783bbe96ed";
    public static final String REDIS_USERSET_KEY = "userSet";

    public static final String REDIS_KEY_PRFIX_MOMENT_LIKE = "momentLike";
    public static final String REDIS_KEY_PRFIX_BOOK_LIKE = "bookLike";
    public static final String REDIS_KEY_AND_FLAG = "_";

    public static final String SESSION_OPENID = "openId";
    public static final String SESSION_USER = "user";

    /**
     * 借阅状态 00-在借 01-正常归还 02-图书超期 根据归还时间判断状态
     */
    public static final String BORROW_ING = "00";
    public static final String BORROW_BACK = "01";
    public static final String BORROW_OVERDUE = "02";

    /**
     * 分页操作，page当前页数，pageSize每页限时条数
     */
    public static final int PAGE = 1;
    public static final int PAGE_SIZE = 10;

    /**
     * 图书摘要的最大字数
     */
    public static final int SUMMARY_MAX_LENGTH = 200;

    /**
     * 图书状态 00-可借 01-借出 02-下架
     */
    public static final String BOOK_STATUS_AVAILABLE = "00";
    public static final String BOOK_STATUS_OUT = "01";
    public static final String BOOK_STATUS_UNAVAILABLE = "02";

    /**
     * 图书借阅类型 00-借阅 01-交换  02-赠送
     */
    public static final String BOOK_BORROW = "00";
    public static final String BOOK_SWAP = "01";
    public static final String BOOK_DONATE = "02";

    /**
     * 图书可借阅期限 默认30天
     */
    public static final int LASTDAY = 30;

    /**
     * 朋友圈消息状态
     */
    public static final String MOMENTS_STATUS_NORMAL = "0";
    public static final String MOMENTS_STATUS_DELETED = "1";

    /**
     * 点赞状态 00-点赞，01-取消赞
     */
    public static final String LIKE_STATUS_LIKE = "00";
    public static final String LIKE_STATUS_UNLIKE = "01";

    /**
     * 消息发送状态 00-推送中,01-已送达,02-已阅读
     */
    public static final String MESSAGE_SEND_TODO = "00";
    public static final String MESSAGE_SEND_DONE = "01";
    public static final String MESSAGE_READ_DONE = "02";

    /**
     * 用户类型 00-管理员 01-普通用户
     */
    public static final String USER_ADMIN = "00";
    public static final String USER_COMMON = "01";

    /**
     * 错误类别-借书/还书
     */
    public static final String ERROR_BORROW = "borrow";
    public static final String ERROR_RETURN = "return";

    //积分规则名
    public static final String SCORE_DAY_LIMIT = "day_limit";
    public static final String SCORE_UP = "up_score";
    public static final String SCORE_DOWN = "down_score";
    public static final String SCORE_BACK_OWNER = "back_owner_score";
    public static final String SCORE_BACK_BEFORE_DEADLINE = "back_before_deadline_borrower_score";
    public static final String SCORE_BACK_AFTER_DEADLINE = "back_after_deadline";
    public static final String SCORE_PRAISE = "praise";
    public static final String SCORE_TOP_TEN = "top_ten_score";
    public static final String SCORE_CHEAT = "cheat";
    public static final String SCORE_LEND_DAY_LIMIT = "lend_day_limit";
    public static final String SCORE_DOWN_NOT_MINUS = "down_not_minus";
    public static final String SCORE_POST_REVIEW = "post_review";
    public static final String SCORE_DELETE_REVIEW = "delete_review";

    //触发积分获得的行为
    public static final String FROM_UPLOAD = "上传图书";
    public static final String FROM_ON_SHELVE = "上架图书";
    public static final String FROM_OFF_SHELVE = "下架图书";
    public static final String FROM_RETURN_TO_YOU = "借阅者归还图书";
    public static final String FROM_YOU_RETURN = "按期还书";
    public static final String FROM_YOU_RETURN_DELAY = "超期还书";
    public static final String FROM_PRAISE = "对您的图书点赞";
    public static final String FROM_RANK_LIST = "来自排行榜top10得分";
    public static final String FROM_CHEAT = "系统检测到您作弊";
    public static final String FROM_CANCEL_PRAISE = "对您的图书取消点赞";
    public static final String FROM_DRAW_LOTTERY = "积分抽奖";
    public static final String FROM_WIN_LOTTERY = "积分奖品";
    public static final String FROM_POST_REVIEW = "发表评论";
    public static final String FROM_DELETE_REVIEW = "删除评论";
    public static final String FROM_SHARE_ACTIVITY = "享读会活动积分奖励";

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String SUCCESS_UP = "success_up";
    public static final String SUCCESS_DOWN = "success_down";

    /**
     * 中奖纪录状态
     */
    public static final String LOTTERY_HISTORY_INIT = "00";
    public static final String LOTTERY_HISTORY_HANDLED = "01";

    /**
     * 奖品类型
     */
    public static final String PRIZE_TYPE_PHONE = "01";
    public static final String PRIZE_TYPE_SCORE = "02";

    /**
     * 未中奖
     */
    public static final String PRIZE_ITEM_NO = "0";

    /* 图灵机器人返回数据类型状态码(官方固定) */
    /** 文本 */
    public static final String TEXT_CODE = "100000";
    /** 列车 */
    public static final String TRAIN_CODE = "305000";
    /** 航班 */
    public static final String FLIGHT_CODE = "306000";
    /** 链接类 */
    public static final String LINK_CODE = "200000";
    /** 新闻 */
    public static final String NEWS_CODE = "302000";
    /** 菜谱、视频、小说 */
    public static final String MENU_CODE = "308000";
    /** key的长度错误（32位） */
    public static final String LENGTH_WRONG_CODE = "40001";
    /** 请求内容为空 */
    public static final String EMPTY_CONTENT_CODE = "40002";
    /** key错误或帐号未激活 */
    public static final String KEY_WRONG_CODE = "40003";
    /** 当天请求次数已用完 */
    public static final String NUMBER_DONE_CODE = "40004";
    /** 暂不支持该功能 */
    public static final String NOT_SUPPORT_CODE = "40005";
    /** 服务器升级中 */
    public static final String UPGRADE_CODE = "40006";
    /** 服务器数据格式异常 */
    public static final String DATA_EXCEPTION_CODE = "40007";

    /* spdbcc机器人 */
    public static final String EMPTY_PACKET_TIPS = "请不要发空包给我。多次发送空包会被管理员锁定账户，谢谢合作。";
    public static final String INVALID_PACKET_TIPS = "请不要发无效的红包给我。多次发送会被管理员锁定账户，谢谢合作。";
    public static final String RESEND_PACKET_TIPS = "由于浦发网络原因导致无法判断红包有效性，请稍后重试";

    public static final String PACKET_VALID = "0";
    public static final String PACKET_INVALID = "1";

}