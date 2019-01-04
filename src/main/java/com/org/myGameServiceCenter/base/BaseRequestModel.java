package com.org.myGameServiceCenter.base;


/**
 * *  @Author: gaozhixing
 * *  @Company: exsun
 * *  @Date: 2018/3/18 21:37
 * *  @Description:
 **/
public class BaseRequestModel {

    public BaseRequestModel() {
    }

    int code;
    String error;
    String msg;
    Object detail;
    Object retain;

    static public BaseRequestModel makeErrorRequestModel(String msg) {
        BaseRequestModel baseRequestModel = new BaseRequestModel();
        baseRequestModel.setCode(ErrorCode.FAILE);
        baseRequestModel.setMsg(msg);
//        baseRequestModel.setDetail(new StringBuffer());
        return baseRequestModel;
    }

    static public BaseRequestModel makeExceptionRequestModel(String error) {
        BaseRequestModel baseRequestModel = new BaseRequestModel();
        baseRequestModel.setCode(ErrorCode.FAILE);
        baseRequestModel.setError(error);
        baseRequestModel.setMsg(error);
        return baseRequestModel;
    }

    static public BaseRequestModel makeExceptionRequestModel(String msg, String error) {
        BaseRequestModel baseRequestModel = new BaseRequestModel();
        baseRequestModel.setCode(ErrorCode.FAILE);
        baseRequestModel.setError(error);
        baseRequestModel.setMsg(msg);
        return baseRequestModel;
    }

    static public BaseRequestModel makeOkRequestModel(String msg) {
        BaseRequestModel baseRequestModel = new BaseRequestModel();
        baseRequestModel.setCode(ErrorCode.OK);
        baseRequestModel.setMsg(msg);
        return baseRequestModel;
    }

    static public BaseRequestModel makeOkRequestModel(String msg, Object object) {
        BaseRequestModel baseRequestModel = new BaseRequestModel();
        baseRequestModel.setCode(ErrorCode.OK);
        baseRequestModel.setMsg(msg);
        baseRequestModel.setDetail(object);
        return baseRequestModel;
    }

    static public BaseRequestModel makeOkRequestModel(String msg, Object object, Object object1) {
        BaseRequestModel baseRequestModel = new BaseRequestModel();
        baseRequestModel.setCode(ErrorCode.OK);
        baseRequestModel.setMsg(msg);
        baseRequestModel.setDetail(object);
        baseRequestModel.setRetain(object1);

        return baseRequestModel;
    }

    static public BaseRequestModel makeIdentityFailureResponse(String msg) {
        BaseRequestModel baseRequestModel = new BaseRequestModel();
        baseRequestModel.setCode(ErrorCode.IDENTITY_FAILURE);
        baseRequestModel.setMsg(msg);
        return baseRequestModel;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public Object getRetain() {
        return retain;
    }

    public void setRetain(Object retain) {
        this.retain = retain;
    }
}
