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
            mapper.getAll().forEach(article -> log.info(article.toString()));

            Article article1 = new Article();
            article1.setId(2L);
            article1.setAuthor("test");
            article1.setTitle("test");
            Article article2 = new Article();
            article2.setId(3L);
            article2.setAuthor("foo");
            article2.setTitle("foo");
            log.info(String.valueOf(mapper.insert(article1)));
            log.info(String.valueOf(mapper.insert(article2)));
        };
    }
}
