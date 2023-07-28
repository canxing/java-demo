package org.example.spring.http.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

// 使用 SpringRunner 进行测试
@RunWith(SpringRunner.class)
// 启动一个测试服务器，使用随机端口
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloResourceTest {
    // 获取随机端口
    @Value(value = "${local.server.port}")
    private int port;

    // 发送 http 请求模板
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHello() {
        String result = this.restTemplate.getForObject("http://localhost:" + port + "/test/hello.json",
                String.class);
        assertThat(result).contains("hello");
        result = this.restTemplate.getForObject("http://localhost:" + port + "/test/hello.json?name=abc",
                String.class);
        assertThat(result).contains("hello, abc");
    }

    @Test
    public void testHelloWithName() {
        String result = this.restTemplate.getForObject("http://localhost:" + port + "/test/hello/abc.json",
                String.class);
        assertThat(result).contains("hello, abc");
    }

    @Test
    public void testPostHello() {
        HelloResource.HelloInfo helloInfo = new HelloResource.HelloInfo();
        helloInfo.setName("abc");
        String result = this.restTemplate.postForObject("http://localhost:" + port + "/test/hello.json", helloInfo,
                String.class);
        assertThat(result).contains("hello, abc");
    }
}