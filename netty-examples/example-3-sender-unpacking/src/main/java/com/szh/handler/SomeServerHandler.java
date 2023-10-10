package com.szh.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/10 12:30
 */
public class SomeServerHandler extends ChannelInboundHandlerAdapter {


    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("from client " + ctx.channel().remoteAddress() + ":" + msg);
//
//
//        ctx.writeAndFlush("This is server");
        System.out.println("Server端接收到的第【" + ++counter + "】个数据包：" + msg);

        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
