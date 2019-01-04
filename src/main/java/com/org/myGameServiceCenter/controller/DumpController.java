package com.org.myGameServiceCenter.controller;

import com.org.myGameServiceCenter.service.MsgHandlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "jt808下发数据服务")
@Controller
@RestController
@RequestMapping("/bubiao")
public class DumpController {

    @Autowired
    MsgHandlerService msgHandlerService;

    @ApiOperation(value = "jt808下发数据服务", notes = "jt808下发数据服务")
    @PostMapping("/jt808Send")
    public String jt808Send(@RequestParam String clientId,
                            @RequestParam String msgId,
                            String msgBody
    ) {
        return msgHandlerService.jt808Send(clientId, msgId, msgBody);
    }
}
