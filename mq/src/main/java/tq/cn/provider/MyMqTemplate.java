package tq.cn.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import tq.cn.entity.MyMessage;
import tq.cn.exchange.MyDirectExchange;

@Service
public class MyMqTemplate {

    @Autowired
    private ApplicationContext applicationContext;

    public void convertAndSend(String exchangeName, String routingKey, Object message) {
        MyDirectExchange bean = (MyDirectExchange) applicationContext.getBean(exchangeName);
        System.out.println("第一步");
        //将消息储存到队列中
        try {
            bean.sendMessage(new MyMessage(message), routingKey);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
