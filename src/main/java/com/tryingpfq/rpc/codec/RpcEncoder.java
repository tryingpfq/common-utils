package com.tryingpfq.rpc.codec;

import com.tryingpfq.rpc.Constants;
import com.tryingpfq.rpc.compress.Compressor;
import com.tryingpfq.rpc.compress.CompressorFactory;
import com.tryingpfq.rpc.protocol.Header;
import com.tryingpfq.rpc.protocol.Message;
import com.tryingpfq.rpc.serialization.Serialization;
import com.tryingpfq.rpc.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public class RpcEncoder extends MessageToByteEncoder<Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        Header header = msg.getHeader();

        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        out.writeByte(header.getExtraInfo());
        out.writeLong(header.getMessageId());

        Object context = msg.getContext();
        if (Constants.isHeartBeat(header.getExtraInfo())) {
            out.writeInt(0);
            return;
        }
        Serialization serialization = SerializationFactory.get(header.getExtraInfo());
        Compressor compressor = CompressorFactory.get(header.getExtraInfo());
        byte[] payLoad = compressor.compress(serialization.serialize(context));
        out.writeInt(payLoad.length);
        out.writeBytes(payLoad);
    }
}
