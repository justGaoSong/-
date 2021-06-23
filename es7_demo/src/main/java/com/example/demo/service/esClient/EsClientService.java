package com.example.demo.service.esClient;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class EsClientService {
    @Resource
    private RestHighLevelClient esRestClient;

    public Boolean existIndex(String indexName) {
        try{
            GetIndexRequest request=new GetIndexRequest(indexName);
            Boolean exists=esRestClient.indices().exists(request, RequestOptions.DEFAULT);
            return exists;
        }catch (Exception e){
            return false;
        }

    }

    public List<String> analyzeSentence(String input){
        return null;
    }

    public Boolean createIndex(String indexName,String mappingContent){
        return false;
    }
}
