package com.zgw.encoder;

import com.zgw.Facede.GameModel;
import com.zgw.factory.BaseTank;
import com.zgw.netty.TankClient;
import com.zgw.tankFrame.Direct;
import com.zgw.tankFrame.GoodTank;
import io.netty.channel.Channel;

import java.io.*;

import java.util.UUID;

public class TankStateMsg  extends Msg{

    public int x,y;//8
    public Direct direct;//4
    public boolean isMoving;//1
    public UUID id;//16

    public TankStateMsg() {
    }

    public TankStateMsg(BaseTank tank){
        this.x=tank.getRectangle().x;
        this.y=tank.getRectangle().y;
        this.direct=tank.getDirect();
        this.isMoving=tank.isMoving();
        this.id=tank.getUuid();
    }

    /**
     *
     * @return 返回把TankStateMsg写到内存的字节数组
     */
    @Override
    public byte[] toBytes(){
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
            dos.writeBoolean(isMoving);
            dos.writeLong(id.getMostSignificantBits());//高64位
            dos.writeLong(id.getLeastSignificantBits());//低64位
            dos.flush();
            bytes=baos.toByteArray();
        }catch (Exception e){

        }finally {
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

    @Override
    public void handle() {
        if( GameModel.getInstance().getMyTank().getUuid().equals(id) ||
                GameModel.getInstance().findTank(id)  ) return;
        System.out.println(this);
        new GoodTank(this);
        TankClient.INSTANCE.send(new TankStateMsg(GameModel.getInstance().myTank));
    }

    public MsgType getMsgType() {
        return MsgType.TankJoin;
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
            this.isMoving=dis.readBoolean();
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
        return "TankStateMsg{" +
                "x=" + x +
                ", y=" + y +
                ", direct=" + direct +
                ", isMoving=" + isMoving +
                ", id=" + id +
                '}';
    }
}
