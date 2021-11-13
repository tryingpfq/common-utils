package com.tryingpfq.nettyheart.codec;

import com.tryingpfq.nettyheart.msg.MsgData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * 编码器
 * @Author tryingpfq
 * @Date 2020/5/23
 */
public class MsgPckEncoder extends MessageToByteEncoder<MsgData> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MsgData msgData, ByteBuf byteBuf) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] write = messagePack.write(msgData);

        byteBuf.writeBytes(write);
    }
}
