package com.zgw.encoder;

import com.zgw.Facede.GameModel;
import com.zgw.Facede.GameObject;
import com.zgw.factory.BaseBullet;
import com.zgw.factory.BaseTank;
import com.zgw.tankFrame.Bullet;
import com.zgw.tankFrame.Direct;

import java.awt.*;
import java.io.*;
import java.util.UUID;

public class BulletMsg extends  Msg {

    public int x;
    public int y;
    public Direct direct;
    public UUID uuid;
    public UUID owner;

    public BulletMsg() {
    }

    public BulletMsg(BaseBullet bullet) {
        Rectangle rectangle = bullet.getRectangle();
        this.x= rectangle.x;
        this.y=rectangle.y;
        this.direct=bullet.getDirect();
        this.uuid=bullet.getUuid();
        this.owner=bullet.getOwner().getUuid();
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
            dos.writeLong(uuid.getMostSignificantBits());//高64位
            dos.writeLong(uuid.getLeastSignificantBits());//低64位
            dos.writeLong(owner.getMostSignificantBits());//高64位
            dos.writeLong(owner.getLeastSignificantBits());//低64位
            dos.flush();
            bytes=baos.toByteArray();
        }catch (Exception e){}
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

        if( GameModel.getInstance().getMyTank().getUuid().equals(this.owner)) return;

         new Bullet(this);


    }

    public MsgType getMsgType() {
        return MsgType.BulletNew;
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
            this.uuid=new UUID(dis.readLong(),dis.readLong());
            this.owner=new UUID(dis.readLong(),dis.readLong());
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
        return "BulletMsg{" +
                "x=" + x +
                ", y=" + y +
                ", direct=" + direct +
                ", uuid=" + uuid +
                ", owner=" + owner +
                '}';
    }
}
