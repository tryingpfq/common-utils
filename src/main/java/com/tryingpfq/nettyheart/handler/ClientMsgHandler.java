package com.tryingpfq.nettyheart.handler;


import com.tryingpfq.nettyheart.bootstrap.Client;
import com.tryingpfq.nettyheart.msg.MsgData;
import com.tryingpfq.nettyheart.msg.Type;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author tryingpfq
 * @Date 2020/5/23
 */
public class ClientMsgHandler extends AbstractHandler {
    private AtomicInteger in = new AtomicInteger(1);

    private Client client;

    public ClientMsgHandler(Client client) {
        this.client = client;
    }

    @Override
    public void handlerMsg(ChannelHandlerContext context, MsgData msgData) {
        System.out.println("recevie from Server " + msgData.toString());
        MsgData send = MsgData.valueOf(Type.Msg.getType(), String.valueOf(in.getAndDecrement()) );
        context.channel().writeAndFlush(send);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("channelInactive,req Connected");
        client.doConnect();
    }
}
