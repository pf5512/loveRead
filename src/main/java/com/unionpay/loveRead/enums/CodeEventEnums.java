package com.unionpay.loveRead.enums;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/8/3 上午9:50  
 */
public enum CodeEventEnums {
    BORROW("1", "借阅"),
    RETURN("2", "归还");

    private String code;
    private String message;

    private CodeEventEnums(final String code, final String message) {
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
