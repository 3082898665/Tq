package tq.cn.last;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tq.cn.entity.MyMessage;

//持久化
@Component
public class Persistence {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY = "message";

    //持久化队列里的消息
    public void AddMessage(String queueName, Object message){
        System.out.println("持久化~~~~~~");
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        listOps.leftPush(KEY + queueName, message); // 使用LPUSH将消息推入队列左侧
    }

    //返回指定队列的消息
    public MyMessage getMessage(String queueName){
        if (isQueueEmpty(queueName)){
            return null;
        }else {
            return new MyMessage(queueName, dequeueMessage(queueName));
        }
    }

    // 检查队列是否为空
    public boolean isQueueEmpty(String queueName) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        Long size = listOps.size(KEY + queueName);
        return size == 0;
    }

    // 从队列中检索并移除消息
    public Object dequeueMessage(String queueName) {
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        return listOps.rightPop(KEY + queueName); // 使用RPOP从队列右侧弹出消息
    }
}
