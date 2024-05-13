package tq.cn.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import tq.cn.entity.MyMessage;

public class MyMessageEncoder extends MessageToByteEncoder<MyMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MyMessage msg, ByteBuf out) throws Exception {
        byte[] bytes = msg.toString().getBytes(CharsetUtil.UTF_8);
        out.writeBytes(bytes);
    }
}
