package com.szh.schedule;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/12 09:50
 */
public class ScheduleClientHandler extends ChannelInboundHandlerAdapter {

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

        ScheduledFuture<?> scheduledFuture = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                String msg = "demus from client";
                System.out.println(msg);
                channel.writeAndFlush(msg);

            } else {
                System.out.println("client closed !");

                channel.close();
            }
        }, randomInt, TimeUnit.SECONDS);

        scheduledFuture.addListener((future) -> {
           sendHeartBeat(channel);
        });

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client closed !");

    }
}
