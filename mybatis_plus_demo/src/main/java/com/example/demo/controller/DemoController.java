package com.example.demo.controller;

import com.example.demo.domain.TempUser;
import com.example.demo.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoService demoService;

    @ResponseBody
    @RequestMapping(value = "/insertOne",method = RequestMethod.GET)
    public  Integer insertOne(@RequestParam String name,@RequestParam String address){
        TempUser tempUser=new TempUser();
        tempUser.setName(name);
        tempUser.setAddress(address);
        return demoService.insertOne(tempUser);
    }

    @ResponseBody
    @RequestMapping(value = "/logTest",method = RequestMethod.GET)
    public String logTest(@RequestParam String content){
        logger.error(content);
        return content;
    }

}
