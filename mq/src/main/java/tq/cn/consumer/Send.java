package tq.cn.consumer;

import tq.cn.entity.MyMessage;
import tq.cn.provider.correspondence;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//消费者获取消息的请求
public class Send {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void start(String[] queues){
        System.out.println("第一步");

        for (String queue : queues){
            threadPool.submit(() -> {
                new correspondence("localhost").start(queue, new MyMessage(queue, null));
            });
        }
    }
}
