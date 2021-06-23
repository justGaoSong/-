package com.example.demo.service.esClient;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class EsClientService {
    private static final Logger logger = LoggerFactory.getLogger(EsClientService.class);

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
        try{
            AnalyzeRequest request=AnalyzeRequest.withGlobalAnalyzer("ik_smart",input);
            AnalyzeResponse analyze = esRestClient.indices().analyze(request, RequestOptions.DEFAULT);
            List<AnalyzeResponse.AnalyzeToken> tokenList = analyze.getTokens();
            // 循环赋值
            List<String> searchTermList = new ArrayList<>();
            tokenList.forEach(ikToken -> {
                searchTermList.add(ikToken.getTerm());
            });
            return searchTermList;
        }catch (Exception e){
            logger.error("EsClientService.analyzeSentence exp",e);
        }
        return null;
    }

    public Boolean createIndex(String alias,String indexName,String mappingContent){
        try{
            if(StringUtils.containsWhitespace(alias)){

            }else {

            }
        }catch (Exception e){
            logger.error("EsClientService.createIndex exp",e);
            return false;
        }
        return true;
    }
}
