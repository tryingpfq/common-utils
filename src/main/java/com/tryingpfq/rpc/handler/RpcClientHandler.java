package com.tryingpfq.rpc.handler;

import com.tryingpfq.rpc.Constants;
import com.tryingpfq.rpc.protocol.Message;
import com.tryingpfq.rpc.protocol.Response;
import com.tryingpfq.rpc.transport.Connection;
import com.tryingpfq.rpc.transport.NettyResponseFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public class RpcClientHandler extends SimpleChannelInboundHandler<Message<Response>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message<Response> msg) throws Exception {
        NettyResponseFuture responseFuture = Connection.IN_FLIGHT_REQUEST_MAP.remove(msg.getHeader().getMessageId());

        Response response = msg.getContext();

        if (response == null || Constants.isHeartBeat(msg.getHeader().getExtraInfo())) {
            response = new Response();
            response.setCode(Constants.HEARTBEAT_CODE);
        }
        responseFuture.getPromise().setSuccess(response.getResult());
    }
}
