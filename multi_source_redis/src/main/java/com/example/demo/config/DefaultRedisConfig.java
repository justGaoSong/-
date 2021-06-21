package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 默认的redis配置
 */
@Configuration
public class DefaultRedisConfig extends BaseRedisConfig {

    @Value("${first.redis.database}")
    private int dbIndex;

    @Value("${first.redis.host}")
    private String host;

    @Value("${first.redis.port}")
    private int port;

    @Value("${first.redis.password}")
    private String password;

    @Value("${first.redis.timeout}")
    private int timeout;

    @Value("${second.redis.database}")
    private int second_dbIndex;

    @Value("${second.redis.host}")
    private String second_host;

    @Value("${second.redis.port}")
    private int second_port;

    @Value("${second.redis.password}")
    private String second_password;

    @Value("${second.redis.timeout}")
    private int second_timeout;

    @Bean(name = "firstRedisTemplate")
    public RedisTemplate firstRedisTemplate() {
        RedisTemplate template = new RedisTemplate();
        JedisConnectionFactory jedisConnectionFactory=createJedisConnectionFactory(dbIndex,
                host, port, password, timeout);
        jedisConnectionFactory.afterPropertiesSet();//初始化连接pool
        template.setConnectionFactory(jedisConnectionFactory);
        setStrConfigSerializer(template);
        return template;
    }

    @Bean(name = "secondRedisTemplate")
    public RedisTemplate secondRedisTemplate() {
        RedisTemplate template = new RedisTemplate();
        JedisConnectionFactory jedisConnectionFactory=createJedisConnectionFactory(second_dbIndex,
                second_host, second_port, second_password, second_timeout);
        jedisConnectionFactory.afterPropertiesSet();//初始化连接pool
        template.setConnectionFactory(jedisConnectionFactory);
        setStrConfigSerializer(template);
        return template;
    }

}
