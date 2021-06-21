package com.example.demo.utils;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {
    public static void set(RedisTemplate redisTemplate,String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public static String get(RedisTemplate redisTemplate,String key){
        Object result=redisTemplate.opsForValue().get(key);
        if(result==null){
            return null;
        }else {
            return (String)result;
        }
    }

    public static void hset(RedisTemplate redisTemplate,String key,String field,String value){
        redisTemplate.opsForHash().put(key,field,value);
    }

    public static String hget(RedisTemplate redisTemplate,String key,String field){
        Object result=redisTemplate.opsForHash().get(key,field);
        if(result==null){
            return null;
        }else {
            return (String)result;
        }
    }
}
