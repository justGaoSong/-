package com.example.demo.service.esClient;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.print.DocFlavor;
import java.util.*;

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

    public Boolean createIndex(String alias, String indexName, String mappingContent){
        try{
            Boolean exist=existIndex(indexName);
            if(exist){
                return false;
            }else {
                CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                createIndexRequest.source(mappingContent, XContentType.JSON);
                createIndexRequest.setTimeout(TimeValue.timeValueSeconds(60));
                CreateIndexResponse createIndexResponse = esRestClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                if (createIndexResponse != null && createIndexResponse.isAcknowledged()) {
                    logger.info("EsClientService.createIndex创建索引成功");
                    return true;
                }
                if(!StringUtils.isEmpty(alias)){
                    String oldAlias=existAlias(alias);

                }
            }
        }catch (Exception e){
            logger.error("EsClientService.createIndex exp",e);
            return false;
        }
        return false;
    }

    public Map<String,String> getAllAlias() throws Exception{
        GetAliasesRequest request = new GetAliasesRequest();
        GetAliasesResponse getAliasesResponse =  esRestClient.indices().getAlias(request,RequestOptions.DEFAULT);
        Map<String, Set<AliasMetaData>> map = getAliasesResponse.getAliases();
        Map<String,String> alias_index=new HashMap<>();
        for(Map.Entry<String, Set<AliasMetaData>> entry:map.entrySet()){
            String indexName=entry.getKey();
            Set<AliasMetaData> aliasMetaDatas=entry.getValue();
            if(aliasMetaDatas.iterator().hasNext()){
                String alias=aliasMetaDatas.iterator().next().getAlias();
                alias_index.put(alias,indexName);
            }
        }
        return alias_index;
    }

    public String  existAlias(String alias) throws Exception {
        Map<String,String> allAlias=getAllAlias();
        if(allAlias.containsKey(alias)){
            return allAlias.get(alias);
        }else {
            return null;
        }
    }

    public Boolean createAlias(String indexName,String alias){
        try{
            String indexOld=existAlias(alias);
            if(!StringUtils.isEmpty(indexOld)){
                return false;
            }
            if(existIndex(indexName)){
                IndicesAliasesRequest request = new IndicesAliasesRequest();
                IndicesAliasesRequest.AliasActions addAliasAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                        .index(indexName).alias(alias);
                request.addAliasAction(addAliasAction);
                AcknowledgedResponse indicesAliasesResponse = esRestClient.indices()
                        .updateAliases(request, RequestOptions.DEFAULT);
                return indicesAliasesResponse.isAcknowledged();
            }else {
                return false;
            }
        }catch (Exception e){
            logger.error("EsClientService.createAlias exp",e);
        }
        return false;
    }

    public Boolean removeAlias(String indexName,String alias){
        try{
            String indexOld=existAlias(alias);
            if(!StringUtils.isEmpty(indexOld) && indexName.equals(indexName)){
                if(existIndex(indexName)){
                    IndicesAliasesRequest request = new IndicesAliasesRequest();
                    IndicesAliasesRequest.AliasActions addAliasAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE)
                            .index(indexName).alias(alias);
                    request.addAliasAction(addAliasAction);
                    AcknowledgedResponse indicesAliasesResponse = esRestClient.indices()
                            .updateAliases(request, RequestOptions.DEFAULT);
                    return indicesAliasesResponse.isAcknowledged();
                }else {
                    return false;
                }
            }
        }catch (Exception e){
            logger.error("EsClientService.removeAlias exp",e);
        }
        return false;

    }


}
