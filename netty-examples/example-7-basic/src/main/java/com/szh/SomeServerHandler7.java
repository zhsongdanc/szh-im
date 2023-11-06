package com.szh;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/10 12:30
 */
public class SomeServerHandler7 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Object name = ctx.channel().attr(AttributeKey.valueOf("name")).get();
        System.out.println("name=" + name);

        Object city = ctx.channel().attr(AttributeKey.valueOf("city")).get();
        System.out.println("city=" + city);


        System.out.println("from client " + ctx.channel().remoteAddress() + ":" + msg);


        ctx.writeAndFlush("This is server");
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
