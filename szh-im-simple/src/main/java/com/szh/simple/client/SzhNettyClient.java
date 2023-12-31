package com.szh.simple.client;

import com.szh.simple.handler.ClientEchoHelloHandler;
import com.szh.simple.handler.ServerEchoHelloHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/9 15:24
 */
public class SzhNettyClient {

    public static void main(String[] args) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)

                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new ClientEchoHelloHandler());
                    }
                });

        bootstrap.connect("127.0.0.1", 9909).sync();
    }
}
