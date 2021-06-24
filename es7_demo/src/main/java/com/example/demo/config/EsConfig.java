package com.example.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EsConfig {
    @Value("${es7.host}")
    private String es7Host;

    @Value("${es7.port}")
    private Integer es7Port;

    @Bean(name = "esRestClient")
    public RestHighLevelClient esRestClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(es7Host, es7Port, "http")));
		return client;
    }

    /**
     *方式二 使用账号密码连接
     **/
//    @Bean
//    public RestHighLevelClient esRestClient(){
//        RestClientBuilder builder = RestClient.builder(
//                new HttpHost("21.145.229.153",9200,"http"),
//                new HttpHost("21.145.229.253",9200,"http"),
//                new HttpHost("21.145.229.353",9200,"http"));
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider .setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("elastic","1qaz!QAZ"));
//        builder.setHttpClientConfigCallback(f->f.setDefaultCredentialsProvider(credentialsProvider ));
//        RestHighLevelClient restClient = new RestHighLevelClient (builder);
//        return client;
//    }

}
