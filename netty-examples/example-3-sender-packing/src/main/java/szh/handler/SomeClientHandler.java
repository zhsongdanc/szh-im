package szh.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2023/10/10 12:30
 */
public class SomeClientHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger count = new AtomicInteger(0);

    private String message = "Hello,demus!";



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
//        ByteBuf buffer = Unpooled.buffer(bytes.length);
//        buffer.writeBytes(bytes);
//        ctx.writeAndFlush(buffer);


        ByteBuf buffer = null;
        for (int i = 0; i < 1000; i++) {
            buffer = Unpooled.buffer(bytes.length);
            buffer.writeBytes(bytes);
            ctx.writeAndFlush(buffer);
        }


    }
}
