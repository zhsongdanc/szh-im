package com.szh.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/10 12:31
 */
public class NettyServer4 {

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            // todo 看一下这里如果实际长度大于maxLength，直接异常，但不是LineBasedFrameDecoder中说的TooLongFrameException
                            ch.pipeline().addLast(new LineBasedFrameDecoder(200));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new SomeServerHandler4());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(9909).sync();
            System.out.println("服务器启动成功。监听的端口号为：9909");

            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
