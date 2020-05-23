package com.tryingpfq.nettyheart.bootstrap;

import com.tryingpfq.nettyheart.codec.MsgPckDecoder;
import com.tryingpfq.nettyheart.codec.MsgPckEncoder;
import com.tryingpfq.nettyheart.handler.ClientMsgHandler;
import com.tryingpfq.nettyheart.handler.ServerMsgHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Author tryingpfq
 * @Date 2020/5/23
 */
public class Server {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1023)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(0, 0, 5));
                            pipeline.addLast(new MsgPckDecoder());
                            pipeline.addLast(new MsgPckEncoder());
                            pipeline.addLast(new ServerMsgHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(3322).sync();
            System.err.println("start port:" + 3322);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
