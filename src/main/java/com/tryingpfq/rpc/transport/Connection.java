package com.tryingpfq.rpc.transport;

import com.tryingpfq.rpc.protocol.Message;
import com.tryingpfq.rpc.protocol.Request;
import com.tryingpfq.rpc.protocol.Response;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.Getter;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
@Getter
public class Connection implements Closeable {

    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    public final static Map<Long, NettyResponseFuture> IN_FLIGHT_REQUEST_MAP = new ConcurrentHashMap<>();

    private ChannelFuture channelFuture;

    private AtomicBoolean isConnected = new AtomicBoolean(false);

    public Connection(ChannelFuture channelFuture, boolean isConnected) {
        this.channelFuture = channelFuture;
        this.isConnected.set(isConnected);
    }

    public NettyResponseFuture<Response> request(Message<Request> message, long timeOut) {
        // 生成并设置消息ID
        long messageId = ID_GENERATOR.incrementAndGet();
        message.getHeader().setMessageId(messageId);
        // 创建消息关联的Future
        NettyResponseFuture responseFuture = new NettyResponseFuture(System.currentTimeMillis(),
                timeOut, message, channelFuture.channel(), new DefaultPromise(new DefaultEventLoop()));
        // 将消息ID和关联的Future记录到IN_FLIGHT_REQUEST_MAP集合中
        IN_FLIGHT_REQUEST_MAP.put(messageId, responseFuture);
        try {
            channelFuture.channel().writeAndFlush(message); // 发送请求
        } catch (Exception e) {
            // 发送请求异常时，删除对应的Future
            IN_FLIGHT_REQUEST_MAP.remove(messageId);
            throw e;
        }
        return responseFuture;
    }

    @Override
    public void close() throws IOException {
        channelFuture.channel().close();
    }
}
