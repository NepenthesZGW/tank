package com.zgw.encoder;

import com.zgw.Facede.GameModel;
import com.zgw.factory.BaseTank;
import java.awt.*;
import java.io.*;
import java.util.UUID;

public class TankStopMsg extends Msg {

    UUID uuid;
    int x,y;

    public TankStopMsg() {
    }

    public TankStopMsg(BaseTank tank) {
        this.uuid=tank.getUuid();
        Rectangle rectangle = tank.getRectangle();
        this.x= rectangle.x;
        this.y=rectangle.y;
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
            dos.writeLong(uuid.getMostSignificantBits());//高64位
            dos.writeLong(uuid.getLeastSignificantBits());//低64位
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
        if( GameModel.getInstance().getMyTank().getUuid().equals(this.uuid)) return;
        BaseTank tank = (BaseTank)GameModel.getInstance().tankMap.get(this.uuid);
        tank.stop();
    }

    public MsgType getMsgType() {
        return MsgType.TankStop;
    }

    public void parse(byte[] bytes) {
        DataInputStream dis=null;
        ByteArrayInputStream bais=null;
        try{
            bais=new ByteArrayInputStream(bytes);
            dis=new DataInputStream(bais);
            this.x=dis.readInt();
            this.y=dis.readInt();
            this.uuid=new UUID(dis.readLong(),dis.readLong());
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
        return "TankStopMsg{" +
                "uuid=" + uuid +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
