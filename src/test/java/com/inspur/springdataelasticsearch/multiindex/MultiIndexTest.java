package com.inspur.springdataelasticsearch.multiindex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/9
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MultiIndexTest {

    @Autowired
    private ElasticsearchService service;

    @Test
    public void test(){
        Map<String,Integer> map = new HashMap<>();
        map.put("version",1);
        map.put("title",2);
        Boolean aBoolean = service.initMapping(map, "ccc");
        if (aBoolean)
            log.info("初始化索引映射成功！");
        log.info("初始化索引映射失败！");
    }

    @Test
    public void test1(){
        Map<String,Object> map = new HashMap<>();
        map.put("id","001");
        map.put("title","内容001");
        map.put("text","内容详情001");
        map.put("creationtime",new Date());
        map.put("creationuserid","hlk");
        map.put("releaserange","范围1");
        map.put("type","类别1");
        map.put("version","v1");

        Boolean aBoolean = service.indexDocument(map, "ccc");
        if (aBoolean)
            log.info("索引内容成功！");
        log.info("索引内容失败！");
    }

    @Test
    public void test3(){
        List<Map<String, Object>> mapList = service.multiSearch("内容");
        log.info(mapList.toString());
    }

}
