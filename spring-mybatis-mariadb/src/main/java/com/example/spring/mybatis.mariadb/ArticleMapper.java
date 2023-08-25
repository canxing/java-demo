package com.example.spring.mybatis.mariadb;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> getAll();

    Article getById(long id);

    int insert(Article article);

    /**
     * 新增或者更新一条数据
     * 以表的主键作为 key
     */
    int insertOrUpdate(Article article);
}
