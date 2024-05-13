package tq.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tq.cn.annotation.EnableProvider;

@SpringBootApplication
@EnableProvider
public class MqProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqProviderApplication.class, args);
    }
}
