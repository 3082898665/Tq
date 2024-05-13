package tq.cn.annotation;

import org.springframework.context.annotation.Import;
import tq.cn.ThreadPool.ThreadPoolManager;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ThreadPoolManager.class)
public @interface EnableProvider {
}
