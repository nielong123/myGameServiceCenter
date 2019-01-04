package com.org.myGameServiceCenter.controller;


import com.org.myGameServiceCenter.base.JT808Data;
import com.org.myGameServiceCenter.base.kafka.DataBackendMsgPackage;
import com.org.myGameServiceCenter.handler.KafkaMsgHandler;
import com.org.myGameServiceCenter.utils.GsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "测试")
@RestController
public class TestController {

    @Autowired
    KafkaMsgHandler kafkaMsgHandler;

    String serial = "0089";

    @ApiOperation(value = "测试")
    @PostMapping(value = "/test")
    public String test() {
        JT808Data jt808Data = new JT808Data();
        jt808Data.setMsgId("8888");
        jt808Data.setSeries("000d");
        jt808Data.setMsgBody(serial + "00" + "313233343536");
        jt808Data.setTerminalPhone("016818018008");

        DataBackendMsgPackage dataBackendMsgPackage = new DataBackendMsgPackage();
        dataBackendMsgPackage.setContent(GsonUtils.getJsonFromObject(jt808Data));
        dataBackendMsgPackage.setMsg("jt808ProtocolResponse");
        dataBackendMsgPackage.setConHost("DESKTOP-E932G5I");
        kafkaMsgHandler.sendDataToMsgCenter(dataBackendMsgPackage);
//        kafkaMsgHandler.syncFutureUtil.setSyncFuture(jt808Data.getTerminalPhone(), dataBackendMsgPackage);
        log.info("send kafka data test");
        return "test";
    }
}
