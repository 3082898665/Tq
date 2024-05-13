package tq.cn.last;

import tq.cn.entity.MyMessage;

//持久化
public class Persistence {

//    @Autowired
//    private RedisTemplate redisTemplate;

    private static final String KEY = "message";

    //持久化队列里的消息
    public void AddMessage(String queueName, MyMessage message){
        System.out.println("持久化~~~~~~");
//        ListOperations<String, MyMessage> listOps = redisTemplate.opsForList();
//        listOps.leftPush(KEY + queueName, message); // 使用LPUSH将消息推入队列左侧
    }

//    // 从队列中检索并移除消息
//    public String dequeueMessage(String queueName) {
//        ListOperations<String, String> listOps = redisTemplate.opsForList();
//        return listOps.rightPop(queueName); // 使用RPOP从队列右侧弹出消息
//    }
//
//    // 检查队列是否为空
//    public boolean isQueueEmpty(String queueName) {
//        ListOperations<String, String> listOps = redisTemplate.opsForList();
//        Long size = listOps.size(queueName);
//        return size == 0;
//    }
//
//    // 获取队列中的消息数量
//    public long getQueueSize(String queueName) {
//        ListOperations<String, String> listOps = redisTemplate.opsForList();
//        return listOps.size(queueName);
//    }
//
//    // 获取队列中的所有消息（注意：这可能会阻塞如果队列很大）
//    public List<String> getAllMessages(String queueName) {
//        ListOperations<String, String> listOps = redisTemplate.opsForList();
//        return listOps.range(queueName, 0, -1); // 获取队列中的所有消息
//    }
}
