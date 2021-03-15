package com.wxw.suaggerDemo.controller;

import com.wxw.suaggerDemo.service.TemplateInter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/sms/")
@Api(value = "短信controller", tags = {"短信接口"})
public class smsController {

    @Resource
    TemplateInter templateInter;

    @ApiOperation("短信发送接口")
    @ApiImplicitParam(name = "电话号码", type = "String", value = "phone")
    @GetMapping("/testSend")
    public void getAccounts(String phone,String param1,String param2,String param3) {
        Map<String, String> result = new HashMap<>();
        try {
            result = templateInter.sendSms(phone, param1, param2,param3);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
