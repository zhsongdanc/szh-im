package com.szh.simple.server;

import com.szh.simple.handler.ChannelInboundHandler1;
import com.szh.simple.handler.ChannelInboundHandler2;
import com.szh.simple.handler.ChannelInboundHandler3;
import com.szh.simple.handler.ChannelOutboundHandler1;
import com.szh.simple.handler.ChannelOutboundHandler2;
import com.szh.simple.handler.ChannelOutboundHandler3;
import com.szh.simple.handler.ServerEchoHelloHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/9 15:23
 */
public class SzhNettyServer {

    public static void main(String[] args) throws InterruptedException {

        outboundSequence();
    }

    static void inboundSequence() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
//                        channel.pipeline().addLast(new ServerEchoHelloHandler());
                        channel.pipeline().addLast(new ChannelInboundHandler1());
                        channel.pipeline().addLast(new ChannelInboundHandler2());
                        channel.pipeline().addLast(new ChannelInboundHandler3());
                    }
                });

        serverBootstrap.bind(9909).sync();
    }

    static void outboundSequence() throws InterruptedException {


        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
//                        channel.pipeline().addLast(new ServerEchoHelloHandler());
                            channel.pipeline().addLast(new ChannelOutboundHandler1());
                            channel.pipeline().addLast(new ChannelOutboundHandler2());
                            channel.pipeline().addLast(new ChannelOutboundHandler3());
                        }
                    });

            serverBootstrap.bind(9909).sync();
        }finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }


    }
}
