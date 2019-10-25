package com.zgw.netty;

import com.zgw.encoder.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class TankServer {
    private Channel ServerChannel=null;
    private ChannelGroup channels=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void listen(Integer port){
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup(10);

        ServerBootstrap bootstrap=new ServerBootstrap();

        try {
            ChannelFuture cf = bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline()
                                        .addLast(new JoinTankMsgEncoder())
                                        .addLast(new JoinTankMsgDecoder())
                                        .addLast(new ServerHandler());
                        }
                    })
                    .bind(port)
                    .addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (channelFuture.isSuccess()) {
                                ServerChannel = channelFuture.channel();
                                System.out.println("监听成功");
                            } else {
                                System.out.println("监听失败");
                            }
                        }
                    }).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ServerHandler extends SimpleChannelInboundHandler<Msg>{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            channels.add(ctx.channel());
        }
        protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
            if(msg.getMsgType()==MsgType.ClientDie) {
                channels.remove(ctx.channel());
                ctx.channel().close();
                System.out.println("客户端断开");
            }
            System.out.println(msg);
            channels.writeAndFlush(msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            channels.remove(ctx.channel());
            ctx.close();
        }
    }

    public static void main(String[] args) {
        TankServer tankServer=new TankServer();
        tankServer.listen(8888);
    }
}
