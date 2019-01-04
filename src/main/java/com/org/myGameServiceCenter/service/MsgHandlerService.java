package com.org.myGameServiceCenter.service;

import com.org.myGameServiceCenter.base.JT808Data;
import com.org.myGameServiceCenter.base.kafka.DataBackendMsgPackage;
import com.org.myGameServiceCenter.handler.KafkaMsgHandler;
import com.org.myGameServiceCenter.utils.GsonUtils;
import com.org.myGameServiceCenter.utils.HostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgHandlerService {
    @Autowired
    KafkaMsgHandler kafkaMsgHandler;

    public String jt808Send(String clientId, String msgId, String msgBody) {
        String ret = "";
        JT808Data jt808Data = new JT808Data();
        jt808Data.setTerminalPhone(clientId);
        jt808Data.setMsgId(msgId);
        jt808Data.setMsgBody(msgBody);
        DataBackendMsgPackage dataBackendMsgPackage = new DataBackendMsgPackage();
        dataBackendMsgPackage.setMsg("jt808ProtocolPush");
        dataBackendMsgPackage.setConHost(HostUtil.getHostName());
        dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
        dataBackendMsgPackage = kafkaMsgHandler.sendDataToMsgCenter(clientId, dataBackendMsgPackage, 10 * 1000);
        if (dataBackendMsgPackage != null) {
            ret = dataBackendMsgPackage.getContent();
        }
        return ret;
    }
}
