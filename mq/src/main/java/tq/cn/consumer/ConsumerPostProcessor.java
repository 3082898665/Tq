package tq.cn.consumer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import tq.cn.ThreadPool.ThreadPoolManager;
import tq.cn.annotation.MyRabbitListener;
import tq.cn.entity.End;
import tq.cn.provider.correspondence;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerPostProcessor implements BeanPostProcessor {

    //类中的所有方法
//    public static ArrayList<Method> need = new ArrayList<>();
    public static Map<String, End> need = new HashMap<>();

    /**
     * 代理层注入
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    /*@Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 检查 bean 是否包含 MyRabbitListener 注解
        if (bean.getClass().isAnnotationPresent(MyRabbitListener.class)) {
            // 如果包含，可以将 bean 添加到需要处理的集合中
            System.out.println("Class with MyRabbitListener: " + bean.getClass().getName());
            final Method[] methods = bean.getClass().getDeclaredMethods();
            // 遍历所有方法找到 MyRabbitListener 注解的方法
            for (Method method : methods) {
                if (method.isAnnotationPresent(MyRabbitListener.class)) {
                    need.put(bean.getClass().getName(), method);
                    final MyRabbitListener rabbitListener = method.getAnnotation(MyRabbitListener.class);
                    // 这里不需要获取方法的返回类型，但如果你需要，可以使用 method.getReturnType()
                    System.out.println("Method with MyRabbitListener: " + method.getName());
                    System.out.println("Annotation details: " + rabbitListener);
                    // 如果你需要方法的返回类型，可以添加以下代码
                    //请求消息
//                new correspondence("localhost").start(method.getName(), null);
                    Send.start(rabbitListener.queues());
                }
            }
        }
        return bean;
    }*/

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final Method[] methods = bean.getClass().getDeclaredMethods();

        // 遍历所有方法找到 MyRabbitListener 注解的方法
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyRabbitListener.class)) {
                // 获取方法所在的类名
                String className = bean.getClass().getName();

                final MyRabbitListener rabbitListener = method.getAnnotation(MyRabbitListener.class);
                // 输出方法所在的类名以及方法名
                System.out.println("Class with MyRabbitListener: " + className);
                System.out.println("Method with MyRabbitListener: " + method.getName());
                System.out.println("Annotation details: " + rabbitListener);

                need.put(className, new End(rabbitListener.queues()[0], method.getName()));

                // 这里可以执行你需要的逻辑
                Send.start(rabbitListener.queues());
            }
        }
        return bean;
    }

}
