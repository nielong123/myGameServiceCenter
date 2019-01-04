package com.org.myGameServiceCenter.base.kafka;

public class DataBackendMsgPackage {
    protected String Msg;
    protected String conHost;
    protected String content;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getConHost() {
        return conHost;
    }

    public void setConHost(String conHost) {
        this.conHost = conHost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DataBackendMsgPackage{" +
                "Msg='" + Msg + '\'' +
                ", conHost='" + conHost + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
