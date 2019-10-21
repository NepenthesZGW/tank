package com.zgw.MemenTo;

import com.zgw.Facede.GameModel;
import com.zgw.Facede.GameObject;
import com.zgw.factory.BaseTank;
import com.zgw.tankFrame.GoodTank;

import java.io.*;
import java.util.List;

public class Snapshot {

    public static void save() {
        ObjectOutputStream out=null;

        try {
           out=new ObjectOutputStream(new FileOutputStream("Tank.data"));

           out.writeObject(GameModel.getInstance().getMyTank());
           out.writeObject(GameModel.getInstance().gameObjectList);
           out.flush();
            System.out.println("----保存成功----");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("----保存失败----");
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void load() {
        ObjectInputStream in=null;

        try {
            in=new ObjectInputStream(new FileInputStream("Tank.data"));

            BaseTank myTank = (BaseTank)in.readObject();
            List<GameObject> list = (List<GameObject>)in.readObject();
            GameModel.getInstance().myTank= (GoodTank) myTank;
            GameModel.getInstance().gameObjectList=list;
            System.out.println("----读取成功----");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----读取失败----");
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
