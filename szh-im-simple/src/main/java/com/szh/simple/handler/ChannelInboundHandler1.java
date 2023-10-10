package com.szh.simple.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/9 16:35
 */
public class ChannelInboundHandler1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("1111 " + msg);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().pipeline().fireChannelRead("Hello World 1111");
    }

}

