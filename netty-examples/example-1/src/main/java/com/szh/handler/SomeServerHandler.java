package com.szh.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import java.nio.charset.StandardCharsets;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/10 10:14
 */
public class SomeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {



        if (msg instanceof HttpRequest) {
            System.out.println("msg = " + msg.getClass());
            System.out.println("客户端地址 = " + ctx.channel().remoteAddress());

            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("请求方法：" + httpRequest.method().name());
            System.out.println("uri：" + httpRequest.uri());

            ByteBuf body = Unpooled.copiedBuffer("This is netty response !".getBytes(StandardCharsets.UTF_8));
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1
            , HttpResponseStatus.OK, body);

            HttpHeaders headers = response.headers();
            headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            // todo 仔细了解readableBytes
            headers.set(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes());

            ctx.writeAndFlush(response);
        }
//        super.channelRead(ctx, msg);
    }

    /**
     * channel中数据处理出现异常时调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // todo ctx.close会将channel关闭吗
        ctx.close();
//        ctx.channel().close();
        super.exceptionCaught(ctx, cause);
    }
}
