package com.tryingpfq.rpc.transport;

import com.tryingpfq.rpc.protocol.Message;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Promise;
import lombok.Getter;
import lombok.Setter;


/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
@Getter
@Setter
public class NettyResponseFuture<T> {

    private long createTime;

    private long timeOut;

    private Message request;

    private Channel channel;

    private Promise<T> promise;

    public NettyResponseFuture(long createTime, long timeOut, Message request, Channel channel, Promise<T> promise) {
        this.createTime = createTime;
        this.timeOut = timeOut;
        this.request = request;
        this.channel = channel;
        this.promise = promise;
    }
}
