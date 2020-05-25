package com.tryingpfq.nettyheart.handler;

import com.tryingpfq.nettyheart.msg.MsgData;
import com.tryingpfq.nettyheart.msg.Type;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端业务处理handler
 * 为什么继承SimpleChannelInboundHandler 因为不需要考虑释放问题
 * @Author tryingpfq
 * @Date 2020/5/23
 */
public abstract class  AbstractHandler extends SimpleChannelInboundHandler<MsgData> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MsgData msgData) throws Exception {
        int type = msgData.getType();
        switch (type) {
            case 1:
                System.out.println("receive ping body:" + msgData.toString());
                sendPongMsg(channelHandlerContext);
                break;
            case 2:
                System.out.println("get Pong msg,body: " + msgData.getBody());
                break;
            case 3:
                handlerMsg(channelHandlerContext,msgData);
                break;
            default:
                break;
        }
    }

    public void sendPongMsg(ChannelHandlerContext context) {
        MsgData msgData = MsgData.valueOf(Type.Pong.getType(),"pong response fromServer");
        System.out.println("begin response pong");
        context.channel().writeAndFlush(msgData);
    }

    public abstract void handlerMsg(ChannelHandlerContext context, MsgData msgData);
}
