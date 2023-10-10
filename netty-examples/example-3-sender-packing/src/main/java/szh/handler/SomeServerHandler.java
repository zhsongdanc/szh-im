package szh.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;

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
        ByteBuf byteBuf = (ByteBuf) msg;

        byteBuf.toString();
//        ctx.writeAndFlush("This is server");
        // 这里不加 UTF-8 会出错
        System.out.println("Server端接收到的第【" + ++counter + "】个数据包：" + byteBuf.toString(Charset.forName("UTF-8")));


//        if (msg instanceof ByteBuf) {
//
//        }


        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
