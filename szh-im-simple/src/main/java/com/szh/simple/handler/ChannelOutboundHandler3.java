package com.szh.simple.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/9 18:54
 */
public class ChannelOutboundHandler3 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        super.write(ctx, msg, promise);

        System.out.println("333" + msg);

        ctx.write(msg, promise);
    }
}
