package tq.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    public void Test(){
        redisTemplate.opsForValue().set("666", 666);
        System.out.println(redisTemplate.opsForValue().get("666"));
    }
}
