package com.inspur.springdataelasticsearch.aggregation;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/6
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AggregationTest {
    @Autowired
    TestService testService;

    @Test
    public void testSearch(){
        long start_time = System.currentTimeMillis();

        long end_time = System.currentTimeMillis();
        testService.search2("爬虫");
        long end_time2 = System.currentTimeMillis();
        log.info("执行时间1："+(end_time-start_time));
        log.info("执行时间2："+(end_time2-end_time));
    }

    @Test
    public void testIndexDoc(){
        String[] descs = {
                "网络爬虫是一个自动提取网页的程序，它为搜索引擎从万维网上下载网页，是搜索引擎的重要组成。传统爬虫从一个或若干初始网页的URL开始，获得初始网页上",
                "的URL，在抓取网页的过程中，不断从当前页面上抽取新的URL放入队列,直到满足系统的一定停止条件。聚焦爬虫的工作流程较为复杂，需要根据一定的网页分析",
                "算法过滤与主题无关的链接，保留有用的链接并将其放入等待抓取的URL队列。然后，它将根据一定的搜索策略从队列中选择下一步要抓取的网页URL，并重复上" ,
                "述过程，直到达到系统的某一条件时停止。另外，所有被爬虫抓取的网页将会被系统存贮，进行一定的分析、过滤，并建立索引，以便之后的查询和检索；对于聚",
                "焦爬虫来说，这一过程所得到的分析结果还可能对以后的抓取过程给出反馈和指导。"
        };
        String[] brands = {"小米","华为","苹果","魅族","锤子"};
        String[] names = {"无线充电","智能机","面部识别","屏下指纹"};

        long start_time = System.currentTimeMillis();
        for (int i =0;i<10000;i++){
            TestEntity entity = new TestEntity();
            entity.setId(UUID.randomUUID().toString().replace("-","").toUpperCase());
            entity.setName(names[new Random().nextInt(4)]);
            entity.setBrand(brands[new Random().nextInt(5)]);
            entity.setDesc(descs[new Random().nextInt(5)]);
            testService.indexDoc(entity);
        }
        long end_time = System.currentTimeMillis();
        log.info("执行时间："+(end_time-start_time));
    }

    @Test
    public void initMappings(){
        Boolean aBoolean = testService.initMappings();
        if (aBoolean) {
            log.info("初始化mapping成功！");
        } else {
            log.error("初始化mapping失败！");
        }
    }
}
