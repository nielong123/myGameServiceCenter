package com.org.myGameServiceCenter.base;


/**
 * Created by nielong123 on 2018/7/13.
 */
public class BaseException extends RuntimeException {

    private String errorCode;

    private boolean propertiesKey = true;

    public BaseException(String message) {
        super(message);
        this.errorCode = ErrorCode.OTHER_ERROR + "";
    }

    public BaseException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, String message, boolean propertiesKey) {
        super(message);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setPropertiesKey(boolean propertiesKey) {
        this.propertiesKey = propertiesKey;
    }
}
