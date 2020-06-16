package szzii.com.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.function.Function;

/**
 * @author szz
 */
public class ServerHandler extends SimpleChannelInboundHandler<Function> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Function msg) throws Exception {
        Object a = msg.apply("a");
        ctx.channel().writeAndFlush(a);
    }
}
