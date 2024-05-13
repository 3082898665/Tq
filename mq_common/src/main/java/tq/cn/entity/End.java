package tq.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class End {
    private String queueName;

    private String methodName;
}
