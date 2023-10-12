package com.szh.simplechat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/11 13:44
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel curChannel = ctx.channel();
        channelGroup.forEach(channel -> {
            if (channel != curChannel) {
                channel.writeAndFlush(curChannel.remoteAddress() + ":" + msg);

            } else {
                channel.writeAndFlush("me" + ":" + msg);

            }
        });
        System.out.println(curChannel.remoteAddress() + ":" + msg);

//        super.channelRead(ctx, msg);
    }

    // todo 为什么这里直接给客户端发消息，客户端不调用channelRead
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel curChannel = ctx.channel();
        String msg = curChannel.remoteAddress() + "加入聊天！";
        System.out.println(msg);

        channelGroup.add(curChannel);

        channelGroup.forEach(channel -> {
            if (channel == curChannel) {
                channel.writeAndFlush("you" + "加入聊天！");
            } else {
                channel.writeAndFlush(curChannel.remoteAddress() + "加入聊天！");
            }
        });

//        channelGroup.writeAndFlush(curChannel.remoteAddress() + "加入聊天！");
//        ctx.channel().writeAndFlush("666666");


    }

    //
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        String msg = channel.remoteAddress() + "退出聊天！当前人数为:" + channelGroup.size();
        System.out.println(msg);
        channelGroup.writeAndFlush(msg);
    }


}
