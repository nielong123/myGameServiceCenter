package com.org.myGameServiceCenter.dao;

import com.org.myGameServiceCenter.base.JT808Data;
import com.org.myGameServiceCenter.base.kafka.DataBackendMsgPackage;
import com.org.myGameServiceCenter.handler.KafkaMsgHandler;
import com.org.myGameServiceCenter.utils.GsonUtils;
import com.org.myGameServiceCenter.utils.HostUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class JT808Parse {

    @Autowired
    RedisTemplate redisTemplate;

    private final Logger logger = Logger.getLogger(JT808Parse.class);

    public DataBackendMsgPackage jt808Parse(DataBackendMsgPackage dataBackendMsgPackage, KafkaMsgHandler kafkaMsgHandler) {
        String dataContent = dataBackendMsgPackage.getContent();
        dataBackendMsgPackage.setMsg("jt808ProtocolResponse");
        dataBackendMsgPackage.setConHost(HostUtil.getHostName());

        JT808Data jt808Data = GsonUtils.getObjectFromJson(dataContent, JT808Data.class);
        logger.info(" ***********jt808Protocol " + jt808Data.getMsgId());
        logger.info(" ***********jt808Protocol  getTerminalPhone   :" + jt808Data.getTerminalPhone());
        String msgId = jt808Data.getMsgId();
        switch (msgId) {
            case "0100":        //终端注册
                jt808Data.setMsgId("8100");
//                jt808Data.setMsgBody(jt808Data.getSeries() + msgId + "00313233");
                jt808Data.setMsgBody(jt808Data.getSeries() + "00" + "313233343536");
                logger.info("********************************** start ******************************");
                logger.info("return 0100 : " + GsonUtils.getJsonFromObject(jt808Data));
                logger.info("********************************** end ******************************");
                dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
                return dataBackendMsgPackage;
            case "0102"://终端鉴权
                jt808Data.setMsgId("8001");
                jt808Data.setMsgBody(jt808Data.getSeries() + msgId + "00");
                dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
                return dataBackendMsgPackage;
            case "0002"://终端心跳
                jt808Data.setMsgId("8001");
                jt808Data.setMsgBody(jt808Data.getSeries() + msgId + "00");
                dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
                return dataBackendMsgPackage;
            case "0200"://位置信息汇报
                logger.info(" 位置信息汇报: " + jt808Data.getMsgBody());
                redisTemplate.opsForValue().set("JT808_KEY_" + jt808Data.getTerminalPhone(), jt808Data.getMsgBody());
                break;

            case "0702":
                logger.info(" 签到/签退: " + jt808Data.getMsgBody());
                break;

            case "0704"://定位数据批量上传
                jt808Data.setMsgId("8001");
                jt808Data.setMsgBody(jt808Data.getSeries() + msgId + "00");
                dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
                return dataBackendMsgPackage;

            case "0001"://设备应答
                kafkaMsgHandler.syncFutureUtil.setSyncFuture(jt808Data.getTerminalPhone(), dataBackendMsgPackage);
                logger.info("返回终端号:" + jt808Data.getTerminalPhone());
                switch (dataBackendMsgPackage.getContent().substring(dataBackendMsgPackage.getContent().length() - 4, dataBackendMsgPackage.getContent().length() - 2)) {
                    case "00":
                        logger.info("返回内容 命令:" + dataBackendMsgPackage.getContent().substring(dataBackendMsgPackage.getContent().length() - 8, dataBackendMsgPackage.getContent().length() - 4) + "   成功");
                        break;
                    default:
                        logger.info("未知  " + dataBackendMsgPackage.toString());
                        break;
                }
                break;

            default:
                kafkaMsgHandler.syncFutureUtil.setSyncFuture(jt808Data.getTerminalPhone(), dataBackendMsgPackage);
                logger.info(" 未解析 msgId: " + msgId + " msgBody: " + jt808Data.getMsgBody());
                break;
        }
//        if (msgId.equals("0100")) {//终端注册
//            jt808Data.setMsgId("8100");
//            jt808Data.setMsgBody(jt808Data.getSeries() + msgId + "00313233");
//            dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
//            return dataBackendMsgPackage;
//        } else if (msgId.equals("0102")) {//终端鉴权){
//            jt808Data.setMsgId("8001");
//            jt808Data.setMsgBody(jt808Data.getSeries() + msgId + "00");
//            dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
//            return dataBackendMsgPackage;
//        } else if (msgId.equals("0002")) {//终端心跳
//            jt808Data.setMsgId("8001");
//            jt808Data.setMsgBody(jt808Data.getSeries() + msgId + "00");
//            dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
//            return dataBackendMsgPackage;
//        } else if (msgId.equals("0200")) {//位置信息汇报
//            logger.info(" 位置信息汇报: " + jt808Data.getMsgBody());
//            redisTemplate.opsForValue().set(jt808Data.getTerminalPhone(), jt808Data.getMsgBody());
//
////            jt808Data.setMsgId("8001");
////            jt808Data.setMsgBody(jt808Data.getSeries()+msgId+"00");
////            dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
////            return dataBackendMsgPackage;
//        } else if (msgId.equals("0704")) {//定位数据批量上传
//            jt808Data.setMsgId("8001");
//            jt808Data.setMsgBody(jt808Data.getSeries() + msgId + "00");
//            dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
//            return dataBackendMsgPackage;
//        } else if (msgId.equals("0001")) {//设备应答
//            kafkaMsgHandler.syncFutureUtil.setSyncFuture(jt808Data.getTerminalPhone(), dataBackendMsgPackage);
//        } else {
//            kafkaMsgHandler.syncFutureUtil.setSyncFuture(jt808Data.getTerminalPhone(), dataBackendMsgPackage);
//            logger.info(" 未解析 msgId: " + msgId + " msgBody: " + jt808Data.getMsgBody());
//        }
        return null;
    }

}
