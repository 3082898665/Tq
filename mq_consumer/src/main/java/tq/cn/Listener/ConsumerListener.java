package tq.cn.Listener;

import org.springframework.stereotype.Component;
import tq.cn.annotation.MyRabbitHandler;
import tq.cn.annotation.MyRabbitListener;
import tq.cn.config.DirectExchangeConfig;
import tq.cn.entity.MyMessage;

@Component
public class ConsumerListener {

    @MyRabbitHandler
    @MyRabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE)
    public void process(MyMessage testMessage) {
        System.out.println("DirectReceiver消费者收到消息1  : " + testMessage.getData());
    }
}
