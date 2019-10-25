package com.zgw.encoder;

import com.zgw.Facede.GameModel;
import com.zgw.Facede.GameObject;
import com.zgw.tankFrame.Direct;
import com.zgw.tankFrame.Explode;

import java.awt.*;
import java.io.*;
import java.util.UUID;

public class ClientDieMsg  extends Msg{

    UUID uuid;

    public ClientDieMsg() {
    }

    public ClientDieMsg(UUID uuid) {
        this.uuid = uuid;
    }

    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;
        try{
            //ByteArrayOutputStream用于把数据写到内存，变成一个字节数组
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeLong(uuid.getMostSignificantBits());//高64位
            dos.writeLong(uuid.getLeastSignificantBits());//低64位
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
        GameObject gameObject = GameModel.getInstance().removeGameObject(uuid);
        Rectangle rectangle = gameObject.getRectangle();
        new Explode(rectangle.x,rectangle.y);
    }

    public MsgType getMsgType() {
        return MsgType.ClientDie;
    }

    public void parse(byte[] bytes) {
        DataInputStream dis=null;
        ByteArrayInputStream bais=null;
        try{
            bais=new ByteArrayInputStream(bytes);
            dis=new DataInputStream(bais);
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
        return "ClientDieMsg{" +
                "uuid=" + uuid +
                '}';
    }
}
