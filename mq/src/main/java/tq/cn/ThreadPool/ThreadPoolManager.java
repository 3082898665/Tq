package tq.cn.ThreadPool;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ThreadPoolManager {
    private static ExecutorService threadPool;

    // 初始化线程池
    @PostConstruct
    public static void init() {
        // 创建一个线程池，设置核心线程数为5，最大线程数为10
        threadPool = Executors.newFixedThreadPool(10);
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

