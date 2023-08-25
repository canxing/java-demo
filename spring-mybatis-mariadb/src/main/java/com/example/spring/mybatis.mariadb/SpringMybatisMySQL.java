package com.example.spring.mybatis.mariadb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMybatisMySQL {
    private static final Logger log = LoggerFactory.getLogger(SpringMybatisMySQL.class);

    public static void main(String[] args) {
        // 禁止启动 web 服务
        new SpringApplicationBuilder(SpringMybatisMySQL.class).web(WebApplicationType.NONE).run(args);
    }
    @Bean
    public CommandLineRunner demo(ArticleMapper mapper) {
        return args -> {
            Article article1 = createArticle(1, "吴承恩", "西游记");
            Article article2 = createArticle(2, "曹雪芹", "红楼梦");
            Article article3 = createArticle(3, "施耐庵", "水浒传");
            Article article4 = createArticle(4, "小罗", "三国演义");
            //单条插入
            mapper.insert(article1);
            mapper.insert(article2);
            mapper.insert(article3);
            mapper.insert(article4);
            // 全部查询
            mapper.getAll().forEach(article -> log.info(article.toString()));

            // 插入或者更新
            article4 = createArticle(4, "罗贯中", "三国演义");
            mapper.insertOrUpdate(article4);
            log.info(mapper.getById(article4.getId()).toString());

            Article article5 = createArticle(5, "未知", "金瓶梅");
            mapper.insertOrUpdate(article5);
            log.info(mapper.getById(article5.getId()).toString());
        };
    }

    private Article createArticle(long id, String author, String title) {
        Article article = new Article();
        article.setId(id);
        article.setAuthor(author);
        article.setTitle(title);
        return article;
    }
}
