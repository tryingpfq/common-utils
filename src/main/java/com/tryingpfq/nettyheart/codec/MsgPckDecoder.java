package com.tryingpfq.nettyheart.codec;

import com.tryingpfq.nettyheart.msg.MsgData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 解码器
 * @Author tryingpfq
 * @Date 2020/5/23
 */
public class MsgPckDecoder extends ByteToMessageDecoder {

    private static final int MIN_SIZE = 2;

    /**
     * 最大1M
     */
    private static final int MAX_SIZE = 1 * 1024 * 1024;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        int leangth = byteBuf.readableBytes();
        if (leangth < MIN_SIZE) {
            return;
        }
        if (leangth >= MAX_SIZE) {
            return;
        }
        byte[] data = new byte[leangth];

        byteBuf.readBytes(data);
        MessagePack pack = new MessagePack();
        out.add(pack.read(data, MsgData.class));
    }
}
