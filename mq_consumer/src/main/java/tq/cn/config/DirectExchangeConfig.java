package tq.cn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tq.cn.binding.Binging;
import tq.cn.binding.MyBindingBuilder;
import tq.cn.exchange.MyDirectExchange;
import tq.cn.queue.MyQueue;

@Configuration
public class DirectExchangeConfig {

    public static final String DIRECT_QUEUE = "directQueue";
    public static final String DIRECT_ROUTING_KEY = "direct";
    public static final String DIRECT_EXCHANGE = "directExchange";

    @Bean
    public MyQueue directQueue() {
        return new MyQueue(DIRECT_QUEUE, true);
    }

    @Bean
    public MyDirectExchange directExchange(){
        return new MyDirectExchange(DIRECT_EXCHANGE, true);
    }

    @Bean
    public Binging bindingDirectExchange(MyQueue directQueue, MyDirectExchange directExchange) {
        return MyBindingBuilder.bind(directQueue, directExchange, DIRECT_ROUTING_KEY);
    }
}
