package tq.cn.provider;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import tq.cn.coder.MyMessageDecoder;
import tq.cn.coder.MyMessageEncoder;
import tq.cn.consumer.Accept;
import tq.cn.entity.MyMessage;

public class correspondence {
    private final String host;
    private final int port = 5675;

    public correspondence(String host) {
        this.host = host;
    }

    //生产者将消息传给消息服务器,以及获取消息
    public void start(String name, MyMessage message) {
        System.out.println("第二步");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new StringDecoder());
//                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new MyMessageEncoder());
                            pipeline.addLast(new MyMessageDecoder());
                            pipeline.addLast(new SimpleChannelInboundHandler<MyMessage>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
                                    System.out.println(msg.getName());
                                    // 这里处理接收到的消息
                                    Accept.getMessage(msg);
                                }
                            });
                        }
                    });

            // 连接服务器
            ChannelFuture future = bootstrap.connect(host, port).sync();

            // 发送消息到服务器
            message.setName(name);
            future.channel().writeAndFlush(message);
            // 等待连接关闭
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
