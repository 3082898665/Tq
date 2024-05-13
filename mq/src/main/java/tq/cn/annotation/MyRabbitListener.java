package tq.cn.annotation;

import org.springframework.scheduling.annotation.Scheduled;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Scheduled(fixedDelay = 5000)
@Repeatable(RabbitListeners.class)
public @interface MyRabbitListener {
    String[] queues() default {};
}

