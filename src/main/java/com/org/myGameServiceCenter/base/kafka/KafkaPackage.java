package com.org.myGameServiceCenter.base.kafka;


import com.org.myGameServiceCenter.utils.GsonUtils;
import com.org.myGameServiceCenter.utils.HostUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KafkaPackage {
    protected static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    protected String clientId;
    protected String contentId;
    protected String contentType;
    protected String protocol;
    protected String conHost;
    protected String channelId;
    protected String remoteAddr;
    protected String content;
    protected String occurTime;
    protected String milliSecondTime;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getConHost() {
        return conHost;
    }

    public void setConHost(String conHost) {
        this.conHost = conHost;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    public String getMilliSecondTime() {
        return milliSecondTime;
    }

    public void setMilliSecondTime(String milliSecondTime) {
        this.milliSecondTime = milliSecondTime;
    }
    public String seriesKafkaPackage(){
        return GsonUtils.getJsonFromObject(this);
    }
    static public KafkaPackage buildKafkaPackage(String clientId, String contentType,String protocol, String channelId,String remoteAddr, Object content){
        KafkaPackage kafkaPackage=new KafkaPackage();
        kafkaPackage.setClientId(clientId);
        kafkaPackage.setContentId((new ObjectId()).toString());
        kafkaPackage.setContentType(contentType);
        kafkaPackage.setProtocol(protocol);
        kafkaPackage.setConHost(HostUtil.hostName);
        kafkaPackage.setChannelId(channelId);
        kafkaPackage.setRemoteAddr(remoteAddr);
        kafkaPackage.setContent(GsonUtils.getJsonFromObject(content));
        Date currentTime=new Date();
        kafkaPackage.setOccurTime(df.format(currentTime));
        kafkaPackage.setMilliSecondTime(String.valueOf(currentTime.getTime()));
        return kafkaPackage;
    }
    static public KafkaPackage buildKafkaPackage(String inputJson){
        return GsonUtils.getObjectFromJson(inputJson,KafkaPackage.class);
    }
}
