package com.zgw.encoder;

import com.zgw.Facede.GameModel;
import com.zgw.Facede.GameObject;
import com.zgw.factory.BaseTank;
import com.zgw.tankFrame.Direct;
import io.netty.channel.Channel;

import java.awt.*;
import java.io.*;
import java.util.UUID;

public class TankMoveMsg extends Msg {

    UUID id;
    int x,y;
    Direct direct;
    public TankMoveMsg() {

    }
    public TankMoveMsg(BaseTank tank){
        Rectangle rectangle = tank.getRectangle();
        this.x=rectangle.x;
        this.y=rectangle.y;
        this.id=tank.getUuid();
        this.direct=tank.getDirect();
    }
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;
        try{
            //ByteArrayOutputStream用于把数据写到内存，变成一个字节数组
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(direct.ordinal());
            dos.writeLong(id.getMostSignificantBits());//高64位
            dos.writeLong(id.getLeastSignificantBits());//低64位
            dos.flush();
            bytes=baos.toByteArray();
        }catch (Exception e){ }
        finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public void handle() {
        if( GameModel.getInstance().getMyTank().getUuid().equals(this.id)) return;
        BaseTank tank = (BaseTank)GameModel.getInstance().tankMap.get(this.id);
        if(tank!=null){
            Rectangle rectangle = tank.getRectangle();
            rectangle.x=this.x;
            rectangle.y=this.y;
            tank.setDirect(this.direct);
        }
    }

    public MsgType getMsgType() {
        return MsgType.TankStartMoving;
    }

    public void parse(byte[] bytes) {
        DataInputStream dis=null;
        ByteArrayInputStream bais=null;
        try{
            bais=new ByteArrayInputStream(bytes);
            dis=new DataInputStream(bais);
            this.x=dis.readInt();
            this.y=dis.readInt();
            this.direct=Direct.values()[dis.readInt()];
            this.id=new UUID(dis.readLong(),dis.readLong());
        }catch (Exception ex){ }
        finally {
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "TankMoveMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", direct=" + direct +
                '}';
    }
}
