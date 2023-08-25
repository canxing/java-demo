package com.example.spring.mybatis.mariadb;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> getAll();

    int insert(Article article);
}
