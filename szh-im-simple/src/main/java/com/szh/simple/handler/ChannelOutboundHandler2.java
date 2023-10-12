package com.szh.simple.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/9 18:54
 */
public class ChannelOutboundHandler2 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

//        super.write(ctx, msg, promise);
        System.out.println("222" + msg);
        ctx.write(msg, promise);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        ctx.executor().schedule(() -> {
//            ctx.write("");
            ctx.channel().write("hello,222!");
        }, 1L, TimeUnit.SECONDS);

//        ctx.channel().write("hello,222!");


//        super.handlerAdded(ctx);
    }


    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // 只会执行一次
        scheduledExecutorService.schedule(() -> {
            System.out.println("hello, szh!");
        }, 1L, TimeUnit.MILLISECONDS);


//        scheduledExecutorService.scheduleWithFixedDelay();
//        scheduledExecutorService.scheduleWithFixedDelay();
    }
}
