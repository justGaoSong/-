package com.example.demo;

import com.example.demo.domain.Book;
import com.example.demo.repo.BookRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
class Es7DemoApplicationTests {

    //自动注入即可使用
    @Autowired
    private ElasticsearchRestTemplate esRestTemplate;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void existAndCreate(){
        //indexName默认取@Document中的indexName
        Book book=new Book();
        book.setId("trgrtgr5tt");
        //book.setPrice(new BigDecimal(10));
        book.setTag(new ArrayList<>(Arrays.asList("测试32")));
        book.setTitle("测试23");
        Boolean result=esRestTemplate.indexOps(Book.class).exists();
        bookRepository.save(book);
        System.out.println(result);
        System.out.println("结束");
    }

    @Test
    private void insertOne(){

    }

    @Test
    private void bulkInsert(){

    }

}
