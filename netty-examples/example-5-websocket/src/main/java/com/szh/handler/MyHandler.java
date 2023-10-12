package com.szh.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/11 10:55
 */
public class MyHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String text = ((TextWebSocketFrame) msg).text();

        ctx.channel().writeAndFlush(new TextWebSocketFrame("Server resp：" + text));

    }

    // 这个是否会触发链式处理，取决于handler怎么处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
//        super.exceptionCaught(ctx, cause);
    }
}
