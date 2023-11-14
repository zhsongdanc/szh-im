package com.szh;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/25 14:20
 */
public class ChannelInboundHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("2222 " + msg);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().pipeline().fireChannelRead("channel-active 2222");
    }

}