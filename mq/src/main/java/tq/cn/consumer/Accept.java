package tq.cn.consumer;

import tq.cn.annotation.MyRabbitListener;
import tq.cn.entity.MyMessage;

import java.lang.reflect.Method;
import java.util.Set;

public class Accept {

    //返回消息
    public static void getMessage(MyMessage msg){
        //获取监听消费者
        Set<String> strings = ConsumerPostProcessor.need.keySet();

        System.out.println("来了");
        for (String s : strings){
            System.out.println(s);
            System.out.println(msg.getData());
            if (ConsumerPostProcessor.need.get(s).getQueueName().equals(msg.getName())){
                try {
                    // 使用类名获取 Class 对象
                    Class<?> clazz = Class.forName(s);
                    // 使用 Class 对象获取指定方法的 Method 对象
                    Method method = clazz.getDeclaredMethod(ConsumerPostProcessor.need.get(s).getMethodName());
                    // 创建类的实例
                    Object obj = clazz.getDeclaredConstructor().newInstance();
                    // 使用 Method 对象调用方法
                    // 准备方法参数
                    Object[] args = {msg}; // 传入参数的值
                    method.invoke(obj, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }


        /*for (Method method : ConsumerPostProcessor.need) {
            final MyRabbitListener rabbitListener = method.getAnnotation(MyRabbitListener.class);
            // 这里不需要获取方法的返回类型，但如果你需要，可以使用 method.getReturnType()
            System.out.println("Method with MyRabbitListener: " + method.getName());
            System.out.println("Annotation details: " + rabbitListener);
            System.out.println(msg);
        }*/
    }

}
