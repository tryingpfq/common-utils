package com.tryingpfq.nettyheart.handler;


import com.tryingpfq.nettyheart.msg.MsgData;
import com.tryingpfq.nettyheart.msg.Type;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author tryingpfq
 * @Date 2020/5/23
 */
public class ServerMsgHandler extends AbstractHandler {


    @Override
    public void handlerMsg(ChannelHandlerContext context, MsgData msgData) {
        System.out.println("recevie from Client " + msgData.toString());
      //  MsgData send = MsgData.valueOf(Type.Msg.getType(), String.valueOf(in.getAndDecrement()) );
       // context.channel().writeAndFlush(send);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        //连接成功 发送第一条消息
        MsgData msgData = MsgData.valueOf(Type.Msg.getType(), "first messge");
        ctx.channel().writeAndFlush(msgData);
    }
}
