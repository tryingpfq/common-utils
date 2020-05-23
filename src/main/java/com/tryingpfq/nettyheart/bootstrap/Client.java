package com.tryingpfq.nettyheart.bootstrap;

import com.tryingpfq.nettyheart.codec.MsgPckDecoder;
import com.tryingpfq.nettyheart.codec.MsgPckEncoder;
import com.tryingpfq.nettyheart.handler.ClientMsgHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author tryingpfq
 * @Date 2020/5/23
 */
public class Client {
    private Bootstrap b;

    public static void main(String[] args) {
        Client client = new Client();
        client.start(client);
    }

    public void start(Client client){
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        this.b =  new Bootstrap();
        b.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(10, 0, 0));
                        pipeline.addLast(new MsgPckDecoder());
                        pipeline.addLast(new MsgPckEncoder());
                        pipeline.addLast(new ClientMsgHandler(client));
                    }
                });

        ChannelFuture future = doConnect();

        //重连机制
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                System.out.println("连接成功");
            }else{
                //重连
                channelFuture.channel().eventLoop().schedule(() -> doConnect(), 6, TimeUnit.SECONDS);
            }
        });
    }


    public  ChannelFuture doConnect() {
        return b.connect("127.0.0.1", 3322);
    }
}
