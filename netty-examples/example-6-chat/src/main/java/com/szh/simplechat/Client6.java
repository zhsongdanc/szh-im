package com.szh.simplechat;

import com.szh.simplechat.MyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/11 13:42
 */
public class Client6 {

    public static void main(String[] args) throws InterruptedException, IOException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();


            bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

//                        ch.pipeline().addLast(new LineBasedFrameDecoder(4096));

                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new MyClientHandler());
                    }
                });

        ChannelFuture future = bootstrap.connect("127.0.0.1", 9909).sync();
        // todo 关闭的条件是什么？ 如果需要处理，不能随便关闭
//        future.channel().closeFuture().sync();

        InputStreamReader inputStreamReader = new InputStreamReader(System.in, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        future.channel().writeAndFlush(bufferedReader.readLine() + "\r\n");


    }

}
