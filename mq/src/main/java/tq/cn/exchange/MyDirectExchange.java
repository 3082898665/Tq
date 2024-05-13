package tq.cn.exchange;

import tq.cn.ThreadPool.ThreadPoolManager;
import tq.cn.entity.MyMessage;
import tq.cn.provider.correspondence;
import tq.cn.queue.MyQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

//直连交换机
public class MyDirectExchange extends MyAbstractExchange{
    private Map<String, Set<MyQueue>> bindings = new HashMap<>();

    public MyDirectExchange(String name, boolean durable){
        super(name, durable);
    }

    //将key与queue直接绑定
    public void addBinding(String routingKey, MyQueue queue) {
        if (!bindings.containsKey(routingKey)) {
            bindings.put(routingKey, new HashSet<>());
        }
        bindings.get(routingKey).add(queue);
    }

    //根据key找到指定的队列，入队储存
    public void sendMessage(MyMessage message, String routingKey) throws InterruptedException {
        System.out.println("第二步");
        if (bindings.containsKey(routingKey)) {
            for (MyQueue queue : bindings.get(routingKey)) {
                //将消息上传服务器，发给指定队列
                queue.enqueue(message);
                ThreadPoolManager.submitTask(() -> {
                    new correspondence("localhost").start(queue.getName(), message);
                });
            }
        } else {
            System.out.println("No queue bound to routing key: " + routingKey);
        }
    }

    //根据key查询指定队列
    public Map<String, Set<MyQueue>> getBindings() {
        return bindings;
    }
}
