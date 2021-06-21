package com.example.demo.service;

import com.example.demo.domain.TempUser;
import com.example.demo.mapper.demo.TempUserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DemoService {
    @Resource
    private TempUserMapper tempUserMapper;

    public Integer insertOne(TempUser tempUser){
        return tempUserMapper.insert(tempUser);
    }

    public Integer updateUser(TempUser tempUser){
        return tempUserMapper.updateById(tempUser);
    }

}
