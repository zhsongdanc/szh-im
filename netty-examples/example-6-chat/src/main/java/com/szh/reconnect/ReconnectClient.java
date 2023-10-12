package com.szh.reconnect;

import com.szh.schedule.ScheduleClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/12 12:36
 */
public class ReconnectClient {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        try{
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new ReconnectClientHandler(bootstrap));
                        }
                    });

            ChannelFuture future = bootstrap.connect("127.0.0.1", 9909).sync();
//            future.channel().closeFuture().sync();

        }finally {
//            nioEventLoopGroup.shutdownGracefully();
        }
    }


}
