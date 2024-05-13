package tq.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

//消息格式
@Data
@AllArgsConstructor
public class MyMessage implements Serializable {
    private String name;
    private Object data;

    public MyMessage(Object data) {
        this.data = data;
    }
}
