package com.szh.idle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.Instant;
import java.util.Date;

/**
 * @author demussong
 * @describe
 * @date 2023/10/11 22:29
 */
public class IdleEventHandler extends ChannelInboundHandlerAdapter {
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


//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//
//        IdleStateEvent event = (IdleStateEvent) evt;
//        if (event.state() == IdleState.READER_IDLE) {
//            System.out.println(new Date() + "read idle");
//        } else if (event.state() == IdleState.WRITER_IDLE) {
//            System.out.println(new Date() + "write idle");
//
//        } else if (event.state() == IdleState.ALL_IDLE) {
//            System.out.println(new Date() + "all idle");
//
//        }
//
//        super.userEventTriggered(ctx, evt);
//    }

    @Override
// 所有“规定动作”之外的所有事件都可以通过以下方法触发
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            String eventDes = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventDes = "读空闲超时";
                    break;
                case WRITER_IDLE:
                    eventDes = "写空闲超时";
                    break;
                case ALL_IDLE:
                    eventDes = "读和写空闲都超时";
            }
            System.out.println(eventDes);
            //关闭节点，等于关闭Channel
            //我们不用关闭节点，演示效果
            //ctx.close();
        } else {
            //当前处理不了，走默认方法，触发下一个处理器的userEventTriggered方法
            super.userEventTriggered(ctx, evt);
        }
    }




}
