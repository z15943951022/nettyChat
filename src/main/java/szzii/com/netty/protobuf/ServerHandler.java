package szzii.com.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author szz
 */
public class ServerHandler extends SimpleChannelInboundHandler<UserInfo.User> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UserInfo.User msg) throws Exception {
        System.out.println("服务器接收消息"+msg.getName()+":"+msg.getAge());
    }
}
