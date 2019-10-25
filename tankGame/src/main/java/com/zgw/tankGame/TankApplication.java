package com.zgw.tankGame;

import com.zgw.netty.TankClient;
import com.zgw.tankFrame.MainFrame;

import java.lang.reflect.Field;
import java.util.Vector;


public class TankApplication {
    public static void main(String[] args) throws Exception {


        final   MainFrame tankFrame=new MainFrame();
        new Thread(new Runnable() {
            public void run() {
                while (true){

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tankFrame.repaint();
                }
            }
        }
        ).start();
        TankClient tankClient=TankClient.INSTANCE;
        tankClient.connect("127.0.0.1",8888);

    }
    public static void printClassesOfClassLoader(ClassLoader loader) {
        try {
            Field classesF = ClassLoader.class.getDeclaredField("classes");
            classesF.setAccessible(true);
            Vector<Class<?>> classes = (Vector<Class<?>>) classesF.get(loader);
            for (Class c : classes) {
                System.out.println(c);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
