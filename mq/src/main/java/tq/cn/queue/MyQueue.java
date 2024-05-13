package tq.cn.queue;

import tq.cn.entity.MyMessage;

import java.util.LinkedList;
import java.util.Queue;

//队列存储消息
public class MyQueue {
    private String name;
    private boolean durable;
    public MyQueue(String name, boolean durable){
        this.name = name;
        this.durable = durable;
    }

    public String getName() {
        return name;
    }

    private Queue<MyMessage> messages = new LinkedList<>();

    public void enqueue(MyMessage message) {
        messages.add(message);
    }

    public MyMessage dequeue() {
        return messages.poll();
    }

    public boolean isEmpty() {
        return messages.isEmpty();
    }
}
