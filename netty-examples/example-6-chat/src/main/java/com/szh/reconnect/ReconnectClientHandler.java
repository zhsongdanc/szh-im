package com.szh.reconnect;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/12 12:41
 */
public class ReconnectClientHandler extends ChannelInboundHandlerAdapter {

    private Bootstrap bootstrap;

    public ReconnectClientHandler(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    private ScheduledFuture<?> scheduledFuture;

    private GenericFutureListener listener;


    //    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
//    }

    // 每隔一定随机时间发送数据包
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        sendHeartBeat(ctx.channel());
    }


    public void sendHeartBeat(Channel channel) {
        Random random = new Random();
        int randomInt = random.nextInt(5) + 1;

         scheduledFuture = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                String msg = "demus from client";
                System.out.println(msg);
                channel.writeAndFlush(msg);

            } else {
                scheduledFuture.removeListener(listener);


            }
        }, randomInt, TimeUnit.SECONDS);

         listener = (future) -> {
            sendHeartBeat(channel);
        };
        scheduledFuture.addListener(listener);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

//        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 9909);
//        ChannelFuture connectFuture = ctx.channel().connect(socketAddress);

        System.out.println("client closed !");

        scheduledFuture.removeListener(listener);
//        System.out.println("client reconnecting...");

        ChannelFuture connectFuture = bootstrap.connect("127.0.0.1", 9909).sync();

        connectFuture.addListener((future) -> {
            System.out.println("client reconnected !");

        });


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
//        super.exceptionCaught(ctx, cause);
    }
}
