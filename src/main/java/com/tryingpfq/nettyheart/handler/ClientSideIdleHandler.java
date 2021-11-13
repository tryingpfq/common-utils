package com.tryingpfq.nettyheart.handler;

import com.tryingpfq.nettyheart.msg.MsgData;
import com.tryingpfq.nettyheart.msg.Type;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author tryingpfq
 * @date 2020/5/25
 **/
public class ClientSideIdleHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * 允许空闲轮询最大次数
     */
    private volatile int readIdleTimes;

    public static ChannelHandler[] create(){
        return new ChannelHandler[]{
                new IdleStateHandler(6,0,0),
                new ClientSideIdleHandler()
        };
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        readIdleTimes = 0;
        /**
         * 使用SimpleChannelInboundHandler 要注意 进入一次read方法 这个对象的引用计数器会减1
         * 如果不处理需要往下传播的话 必须在本handler加1 否则会抛异常
         */
        msg.retain();
        ctx.fireChannelRead(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                case READER_IDLE:
                    if (readIdleTimes >= 5) {
                        ctx.channel().close();
                        System.out.println(String.format("channel close:%s %s", state, ctx.channel()));
                    }else{
                        readIdleTimes++;
                        System.out.println("client start send ping to Server");
                        MsgData msgData = MsgData.valueOf(Type.Ping.getType(),"ping from clentIdle");
                        ctx.channel().writeAndFlush(msgData);
                    }
                    break;
                case WRITER_IDLE:
                    break;
                case ALL_IDLE:
                    System.out.println(String.format("userEventTriggered state:%s %s", state, ctx.channel()));
                    break;
                default:
                    break;
            }
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }

}
