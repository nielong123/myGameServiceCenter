package com.org.myGameServiceCenter.handler;

import com.org.myGameServiceCenter.base.kafka.DataBackendMsgPackage;
import com.org.myGameServiceCenter.base.kafka.KafkaPackage;
import com.org.myGameServiceCenter.dao.JT808Parse;
import com.org.myGameServiceCenter.utils.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@DependsOn("batchRecordConsumerFactory")
@Service
public class KafkaMsgHandler { // (1)

    protected static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    static public SyncFutureUtil syncFutureUtil = new SyncFutureUtil(100, 20 * 1000);

    @Autowired
    JT808Parse jt808Parse;

    @Autowired
    @Qualifier("firstKafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.producer.topic.dataFromDataBackendToMsgCenter}")
    private String dataFromDataBackendToMsgCenterTopic;

    @KafkaListener(topics = {"${dataFromMsgCenterToDataBackendHost}", "${kafka.consumer.topic.dataFromMsgCenterToDataBackend}"}, containerFactory = "batchRecordConsumerFactory")
    public void batchRecordConsumerListen(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
        try {
            for (ConsumerRecord<?, ?> record : records) {
                String topic = record.topic();
                log.info(" consumer topic: " + topic);
                String msg = record.value().toString();
                KafkaPackage kafkaPackage = KafkaPackage.buildKafkaPackage(msg);
                String content = kafkaPackage.getContent();
                DataBackendMsgPackage dataBackendMsgPackage = GsonUtils.getObjectFromJson(content, DataBackendMsgPackage.class);
                //jtt808协议处理
                log.info("kafka 接收到数据:" + dataBackendMsgPackage.toString());
//                switch (dataBackendMsgPackage.getMsg()) {
//                    case "jt808Protocol":
//                        DataBackendMsgPackage dataPackage = jt808Parse.jt808Parse(dataBackendMsgPackage, this);
//                        if (dataPackage != null) {
//                            //需回复的jt808指令
//                            sendDataToMsgCenter(dataPackage);
//                        }
//                        break;
//                    default:
//                        log.info("get default kafka msg " + dataBackendMsgPackage.getMsg());
//                        break;
//                }
            }
            ack.acknowledge();//手动提交偏移量
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            log.error(" 异常 ", e);
            e.printStackTrace();
        }
    }

    public void sendDataToMsgCenter(DataBackendMsgPackage dataBackendMsgPackage) {
        String sendData = KafkaPackage.buildKafkaPackage(HostUtil.getHostName(), dataFromDataBackendToMsgCenterTopic,
                "",
                HostUtil.getHostName(), HostUtil.getHostName(), dataBackendMsgPackage).seriesKafkaPackage();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(dataFromDataBackendToMsgCenterTopic,
                ByteUtils.reverseString(HostUtil.getHostName()) + "_" + HostUtil.getHostName(), sendData);
//        log.info("send data  topic = " + dataFromDataBackendToMsgCenterTopic + "     key = " + ByteUtils.reverseString(HostUtil.getHostName()) + "_" + HostUtil.getHostName());
    }

    public DataBackendMsgPackage sendDataToMsgCenter(String syncId, DataBackendMsgPackage dataBackendMsgPackage, long timeout) {
        DataBackendMsgPackage ret = null;
        try {
            SyncFuture<Object> syncFuture = syncFutureUtil.getSyncFuture(syncId);
            if (syncFuture != null) {
                sendDataToMsgCenter(dataBackendMsgPackage);
                ret = (DataBackendMsgPackage) syncFuture.get(timeout, TimeUnit.MILLISECONDS);
                syncFutureUtil.removeSyncFuture(syncId);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ret;
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void DataBackendHeartBeat() {
//        log.info("kafka发送心跳包");
        DataBackendMsgPackage dataBackendMsgPackage = new DataBackendMsgPackage();
        dataBackendMsgPackage.setMsg("heartBeat");
        dataBackendMsgPackage.setContent(df.format(new Date()));
        sendDataToMsgCenter(dataBackendMsgPackage);
    }

    private void release(Object msg) {
        try {
            ReferenceCountUtil.release(msg);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

}