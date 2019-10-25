package com.zgw.netty;

import com.zgw.Facede.GameModel;
import com.zgw.encoder.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TankClient {

    private Channel channel=null;
    public static final TankClient INSTANCE=new TankClient();
    private TankClient(){}
    /**
     * 此方法连接服务器
     * 此方法会阻塞 调用 该方法线程
     * @param ip 服务端ip地址
     * @param port 服务端监听端口
     */
    public void connect(String ip,Integer port){
        EventLoopGroup group=new NioEventLoopGroup(1);
        Bootstrap bootstrap=new Bootstrap();
        try {
            ChannelFuture cf = bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline()
                                        //往外写encoder  多个会有顺序
                                        //netty内部的写方法，会默认把encoder走一遍
                                             .addLast(new JoinTankMsgEncoder())
                                        //往里读decoder  多个会有顺序
                                        //netty内部的读方法，会默认把decoder走一遍
                                             .addLast(new JoinTankMsgDecoder())
                                             .addLast(new ClientHanlder());
                        }
                    })
                    .connect(ip, port)
                    .addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if(channelFuture.isSuccess()){
                                    channel=channelFuture.channel();
                                    System.out.println("connect success");
                                }else{
                                    System.out.println("connect fail");
                                }
                        }
                    }).sync();

                    cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }


    }

    public void send(Msg msg){
        if (channel!=null) {
            channel.writeAndFlush(msg);
        }
    }
    public void  close(){
        if(channel!=null)
        channel.close();
    }

    private class ClientHanlder extends SimpleChannelInboundHandler<Msg>{

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new TankStateMsg(GameModel.getInstance().myTank));
        }
        public void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg) throws Exception {
            msg.handle();
        }
    }

}
