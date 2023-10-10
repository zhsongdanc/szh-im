package com.szh.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/10 12:30
 */
public class SomeClientHandler4 extends ChannelInboundHandlerAdapter {

    private AtomicInteger count = new AtomicInteger(0);

    private String message = "Hello,demus!" + System.getProperty("line.separator");



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 1000; i++) {

            ctx.writeAndFlush(message);
        }


    }
}
