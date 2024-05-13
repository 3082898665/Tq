package tq.cn.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import tq.cn.entity.MyMessage;

import java.util.List;

public class MyMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 读取字节序列，解析出name和data字段的值
        String content = in.toString(CharsetUtil.UTF_8);
//        System.out.println(content);

        // 查找name值的开始位置
        int nameStartIndex = content.indexOf("name=") + "name=".length();
        // 查找name值的结束位置
        int nameEndIndex = content.indexOf(",", nameStartIndex);
        // 提取name值
        String name = content.substring(nameStartIndex, nameEndIndex).trim();
        // 查找data值的开始位置
        int dataStartIndex = content.indexOf("data=") + "data=".length();
        // 提取data值
        String data = content.substring(dataStartIndex, content.length() - 1).trim();
        out.add(new MyMessage(name, data));

        //不加这个会重复读一个数据，报错
        in.skipBytes(in.readableBytes());
    }
}

