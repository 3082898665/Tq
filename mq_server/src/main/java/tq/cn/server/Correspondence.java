/*
package tq.cn.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.stereotype.Component;
import tq.cn.ThreadPool.ThreadPoolManager;
import tq.cn.coder.MyMessageDecoder;
import tq.cn.coder.MyMessageEncoder;
import tq.cn.entity.MyMessage;
import tq.cn.last.Persistence;

@Component
public class Correspondence {
    private final int port = 5675;

    //服务启动时及开始监听消息
//    @PostConstruct
    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加解码器和编码器来处理字符串消息
                            pipeline.addLast(new MyMessageEncoder());
                            pipeline.addLast(new MyMessageDecoder());
                            // 添加自定义的消息处理器
                            pipeline.addLast(new MyMessageHandler());
                        }
                    });

            // 绑定端口并开始接收连接
            ChannelFuture future = bootstrap.bind(port).sync();

            // 等待服务器套接字关闭
            future.channel().closeFuture().sync();
        } finally {
            // 关闭所有事件循环组以释放所有资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    // 自定义的消息处理器
    private static class MyMessageHandler extends SimpleChannelInboundHandler<Object> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("第五步");

            if (msg instanceof MyMessage) {
                MyMessage myMessage = (MyMessage) msg;
                if (myMessage.getData() == null){
                    MyMessage message = new Persistence().getMessage(myMessage.getName());
                    // 将消息写回客户端
                    ctx.writeAndFlush(message);
                }else {
                    //储存消息，返回操作结果
                    ThreadPoolManager.submitTask(() -> {
                        new Persistence().AddMessage(myMessage.getName(), myMessage.getData());
                    });
//                    ctx.writeAndFlush(new MyMessage("", ""));
                }
            } else {
                // 如果不是 MyMessage 类型，则进行相应处理
                System.out.println("接收到的消息类型不正确");
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // 当出现异常时关闭连接
            System.out.println("有异常");
            cause.printStackTrace();
            ctx.close();
        }
    }
}
*/
package tq.cn.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tq.cn.ThreadPool.ThreadPoolManager;
import tq.cn.coder.MyMessageDecoder;
import tq.cn.coder.MyMessageEncoder;
import tq.cn.entity.MyMessage;
import tq.cn.last.Persistence;

@Component
public class Correspondence {
    private final int port = 5675;
    private final Persistence persistence;

    // 使用构造函数注入Persistence实例
    @Autowired
    public Correspondence(Persistence persistence) {
        this.persistence = persistence;
    }

    //服务启动时及开始监听消息
    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加解码器和编码器来处理字符串消息
                            pipeline.addLast(new MyMessageEncoder());
                            pipeline.addLast(new MyMessageDecoder());
                            // 添加自定义的消息处理器，并传入Persistence实例
                            pipeline.addLast(new MyMessageHandler(persistence));
                        }
                    });

            // 绑定端口并开始接收连接
            ChannelFuture future = bootstrap.bind(port).sync();

            // 等待服务器套接字关闭
            future.channel().closeFuture().sync();
        } finally {
            // 关闭所有事件循环组以释放所有资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    // 自定义的消息处理器
    private static class MyMessageHandler extends SimpleChannelInboundHandler<Object> {
        private final Persistence persistence;

        // 使用构造函数注入Persistence实例
        public MyMessageHandler(Persistence persistence) {
            this.persistence = persistence;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("第三步");

            if (msg instanceof MyMessage) {
                MyMessage myMessage = (MyMessage) msg;
                System.out.println(myMessage);
                System.out.println(myMessage.getData().getClass().getName());
                if (myMessage.getData().equals("null")){
                    MyMessage message = persistence.getMessage(myMessage.getName()); // 使用注入的Persistence实例
                    System.out.println("取出消息给消费者~~");
                    // 将消息写回客户端
                    ctx.writeAndFlush(message);
                } else {
                    //储存消息，返回操作结果
                    ThreadPoolManager.submitTask(() -> {
                        persistence.AddMessage(myMessage.getName(), myMessage.getData()); // 使用注入的Persistence实例
                    });
                }
            } else {
                // 如果不是 MyMessage 类型，则进行相应处理
                System.out.println("接收到的消息类型不正确");
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // 当出现异常时关闭连接
            System.out.println("有异常");
            cause.printStackTrace();
            ctx.close();
        }
    }
}
