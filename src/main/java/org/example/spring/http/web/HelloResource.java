package org.example.spring.http.web;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloResource {
    // 127.0.0.1:8081/test/hello.json?name=abc
    @GetMapping("hello.json")
    public String helle(@RequestParam(value = "name", required = false) String name) {
        if (name == null || name.isEmpty()) {
            return "hello";
        } else {
            return String.format("hello, %s", name);
        }
    }

    // 从定义的请求路径中获取参数
    @GetMapping("hello/{name}.json")
    public String helloWithName(@PathVariable(value = "name") String s) {
        return String.format("hello, %s", s);
    }

    // requestBody 将请求体转换为对应的 Java 对象，对于 json 请求体，默认使用 jackson
    @PostMapping("hello.json")
    public String postHello(@RequestBody HelloInfo helloInfo) {
        return String.format("hello, %s", helloInfo.getName());
    }

    public static class HelloInfo {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
