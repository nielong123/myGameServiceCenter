package com.org.myGameServiceCenter.base;

public class JT808Data {
    private String terminalPhone;
    private String msgId;
    private String series;
    private String msgBody;

    public String getTerminalPhone() {
        return terminalPhone;
    }

    public void setTerminalPhone(String terminalPhone) {
        this.terminalPhone = terminalPhone;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "JT808Data{" +
                "terminalPhone='" + terminalPhone + '\'' +
                ", msgId='" + msgId + '\'' +
                ", series='" + series + '\'' +
                ", msgBody='" + msgBody + '\'' +
                '}';
    }
}
