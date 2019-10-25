package com.zgw.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class JoinTankMsgDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //tcp的拆包，粘包概念
        //readableBytes可读长度，读过了不计入
        if(byteBuf.readableBytes()<8) return;
        byteBuf.markReaderIndex();//记住位置

        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        int msgLength = byteBuf.readInt();
        if(byteBuf.readableBytes()<msgLength){
            byteBuf.resetReaderIndex();//把读指针回到原来标记的地方
            return;
        }

        byte[] bytes=new byte[msgLength];
        byteBuf.readBytes(bytes);


        Msg msg=null;
        switch (msgType) {
            case TankJoin:
                msg = new TankStateMsg();
                break;
            case TankStartMoving:
                msg = new TankMoveMsg();
                break;
            case TankStop:
                msg=new TankStopMsg();
                break;
            case BulletNew:
                msg=new BulletMsg();
                break;
            case ClientDie:
                msg=new ClientDieMsg();
                break;
            default:
                break;
        }
        msg.parse(bytes);//字节数组赋值，解析字节数组
        list.add(msg);
    }
}
