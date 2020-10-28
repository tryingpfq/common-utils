package com.tryingpfq.rpc.codec;

import com.tryingpfq.rpc.Constants;
import com.tryingpfq.rpc.compress.Compressor;
import com.tryingpfq.rpc.compress.CompressorFactory;
import com.tryingpfq.rpc.protocol.Header;
import com.tryingpfq.rpc.protocol.Message;
import com.tryingpfq.rpc.protocol.Request;
import com.tryingpfq.rpc.protocol.Response;
import com.tryingpfq.rpc.serialization.Serialization;
import com.tryingpfq.rpc.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public class RpcDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < Constants.HEADER_SIZE) {
            LOGGER.warn("this buf size less header size");
            return;
        }

        //记录当前readIndex指针的位置
        in.markReaderIndex();
        short magic = in.readShort();
        if (magic != Constants.MAGIC) {
            in.resetReaderIndex();
            throw new RuntimeException("magic number error: " + magic);
        }
        byte version = in.readByte();
        byte extraInfo = in.readByte();
        long messageId = in.readLong();
        int size = in.readInt();

        Object body = null;

        if (!Constants.isHeartBeat(extraInfo)) {
            //非心跳消息

            if (in.readableBytes() < size) {
                in.resetReaderIndex();
                return;
            }
            //消息体
            byte[] payLoad = new byte[size];
            Serialization serialization = SerializationFactory.get(extraInfo);
            Compressor compressor = CompressorFactory.get(extraInfo);
            if (Constants.isRequest(extraInfo)) {
                body = serialization.deserialize(compressor.unCompress(payLoad), Request.class);
            }else{
                body = serialization.deserialize(compressor.unCompress(payLoad), Response.class);
            }
        }
        Header header = new Header(magic, version, extraInfo, messageId, size);
        Message message = new Message(header, body);
        out.add(message);
    }
}
