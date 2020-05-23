package com.tryingpfq.nettyheart.handler;

import com.tryingpfq.nettyheart.msg.MsgData;
import com.tryingpfq.nettyheart.msg.Type;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

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

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        IdleStateEvent stateEvent = (IdleStateEvent) evt;

        switch (stateEvent.state()) {
            case READER_IDLE:
                handReaderIdele(ctx);
                break;
            case WRITER_IDLE:
                handWriterIdele(ctx);
                break;
            case ALL_IDLE:
                handAllIdele(ctx);
                break;
            default:
                break;
        }
    }

    public void sendPingMsg(ChannelHandlerContext context) {
        MsgData msgData = MsgData.valueOf(Type.Ping.getType(),"pong");
        context.channel().writeAndFlush(msgData);
    }

    public void sendPongMsg(ChannelHandlerContext context) {
        MsgData msgData = MsgData.valueOf(Type.Pong.getType(),"pong");
        context.channel().writeAndFlush(msgData);
    }


    public void handReaderIdele(ChannelHandlerContext context) {
        System.out.println("READER_IDLE");
    }

    public void handWriterIdele(ChannelHandlerContext context) {
        System.out.println("WRITER_IDLE");
    }

    public void handAllIdele(ChannelHandlerContext context) {
        System.out.println("All_IDLE");
    }

    public abstract void handlerMsg(ChannelHandlerContext context, MsgData msgData);
}
