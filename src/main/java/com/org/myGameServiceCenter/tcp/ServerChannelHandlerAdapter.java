package com.org.myGameServiceCenter.tcp;

import com.org.myGameServiceCenter.utils.HexStrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@ChannelHandler.Sharable
public class ServerChannelHandlerAdapter implements ChannelInboundHandler {

    int receiveCount = 0;
//    /**
//     * 注入请求分排器
//     */
//    @Resource
//    private RequestDispatcher dispatcher;

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.error("通道开启");
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.error("通道关闭");
        channelHandlerContext.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        byte[] data = (byte[]) o;
        System.out.println("接收数据 : " + channelHandlerContext.channel().localAddress().toString() + "   " + HexStrUtil.encodeHex(data));
        receiveCount++;
        channelHandlerContext.channel().writeAndFlush("hello~~~".getBytes()).sync();
        System.out.println(" receiveCount = " + receiveCount);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("发送异常，通道关闭" + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

}
