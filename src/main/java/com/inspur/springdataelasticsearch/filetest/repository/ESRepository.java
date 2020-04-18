package com.inspur.springdataelasticsearch.filetest.repository;


import com.inspur.springdataelasticsearch.filetest.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ESRepository extends ElasticsearchRepository<Article,String> {
    Page<Article> findByNameOrText(String name, String text, Pageable pageable);
    List<Article> findByText(String qtext);
}

