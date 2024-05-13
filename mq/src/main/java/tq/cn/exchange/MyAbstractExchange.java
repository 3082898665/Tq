package tq.cn.exchange;

import tq.cn.entity.MyMessage;
import tq.cn.queue.MyQueue;

public abstract class MyAbstractExchange {
    //交换机名称
    private String name;
    //是否持久化
    private boolean durable;
    //绑定指定交换机
    public abstract void addBinding(String routingKey, MyQueue queue);
    //将消息放入指定队列
    public abstract void sendMessage(MyMessage message, String routingKey) throws InterruptedException;

    public MyAbstractExchange(String name, boolean durable) {
        this.name = name;
        this.durable = durable;
    }
}
