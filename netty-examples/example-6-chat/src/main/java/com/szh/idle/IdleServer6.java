package com.szh.idle;

import com.szh.handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/11 13:41
 */
public class IdleServer6 {
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

//                            ch.pipeline().addLast(new LineBasedFrameDecoder(4096));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
//                            ch.pipeline().addLast(new MyServerHandler());
                            ch.pipeline().addLast(new IdleStateHandler(3,5,6));
                            ch.pipeline().addLast(new IdleEventHandler());
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
