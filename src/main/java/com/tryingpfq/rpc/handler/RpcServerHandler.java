package com.tryingpfq.rpc.handler;

import com.tryingpfq.rpc.Constants;
import com.tryingpfq.rpc.invoke.InvokeRunnable;
import com.tryingpfq.rpc.protocol.Message;
import com.tryingpfq.rpc.protocol.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public class RpcServerHandler extends SimpleChannelInboundHandler<Message<Request>> {

    private static Executor executor = Executors.newCachedThreadPool();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message<Request> msg) throws Exception {
        byte extraInfo = msg.getHeader().getExtraInfo();
        if (Constants.isHeartBeat(extraInfo)) {
            ctx.writeAndFlush(msg);
            return;
        }
        //
        executor.execute(new InvokeRunnable(msg, ctx));
    }
}
