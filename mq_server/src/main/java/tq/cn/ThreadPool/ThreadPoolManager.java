/*
package tq.cn.ThreadPool;

import org.springframework.stereotype.Component;
import tq.cn.server.Correspondence;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ThreadPoolManager {
    private static ExecutorService threadPool;

    // 初始化线程池
    @PostConstruct
    public static void init() {
        // 创建一个线程池，最大线程数为1
        threadPool = Executors.newFixedThreadPool(10);
        submitTask(() ->{
            try {
                new Correspondence().start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    // 关闭线程池
    public static void shutdown() {
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdown();
        }
    }

    // 提交任务到线程池
    public static void submitTask(Runnable task) {
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.execute(task);
        }
    }
}

*/
package tq.cn.ThreadPool;

import org.springframework.stereotype.Component;
import tq.cn.server.Correspondence;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ThreadPoolManager {
    private static ExecutorService threadPool;
    private final Correspondence correspondence;

    // 使用构造函数注入Correspondence实例
    public ThreadPoolManager(Correspondence correspondence) {
        this.correspondence = correspondence;
    }

    // 初始化线程池
    @PostConstruct
    public void init() {
        // 创建一个线程池，最大线程数为1
        threadPool = Executors.newFixedThreadPool(10);
        submitTask(() -> {
            try {
                correspondence.start(); // 使用已注入的Correspondence实例
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    // 关闭线程池
    public void shutdown() {
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdown();
        }
    }

    // 提交任务到线程池
    public static void submitTask(Runnable task) {
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.execute(task);
        }
    }
}
