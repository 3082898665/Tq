package tq.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tq.cn.config.DirectExchangeConfig;
import tq.cn.entity.MyMessage;
import tq.cn.provider.MyMqTemplate;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private MyMqTemplate myMqTemplate;

    //生产消息
    @GetMapping("/add")
    public String add(){
        myMqTemplate.convertAndSend(DirectExchangeConfig.DIRECT_EXCHANGE, DirectExchangeConfig.DIRECT_ROUTING_KEY, "消息来咯");
        return "消息生产成功";
    }
}
