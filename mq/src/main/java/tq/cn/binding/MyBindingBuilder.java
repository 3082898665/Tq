package tq.cn.binding;

import tq.cn.exchange.MyDirectExchange;
import tq.cn.queue.MyQueue;

public class MyBindingBuilder {

    public static Binging bind(MyQueue directQueue, MyDirectExchange directExchange, String directRoutingKey) {
        directExchange.addBinding(directRoutingKey, directQueue);
        return new Binging(directExchange.getClass().getName(), directRoutingKey, directQueue.getClass().getName());
    }
}


