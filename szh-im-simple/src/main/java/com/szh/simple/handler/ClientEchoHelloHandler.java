package com.szh.simple.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/9 15:30
 */
public class ClientEchoHelloHandler extends SimpleChannelInboundHandler {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("hello");
    }
}
