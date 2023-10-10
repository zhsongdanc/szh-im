package com.szh.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/10 12:30
 */
public class SomeClientHandler extends SimpleChannelInboundHandler {

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(count.getAndIncrement() + "from server " + ctx.channel().remoteAddress() + ":" + msg);

        ctx.writeAndFlush(count.get() + "This is client!");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // todo 这里用Object类型？
        // todo 下面两个的区别
//        ctx.channel().writeAndFlush("This is client!");
        ctx.writeAndFlush("This is client!");

//        super.channelActive(ctx);
    }
}
