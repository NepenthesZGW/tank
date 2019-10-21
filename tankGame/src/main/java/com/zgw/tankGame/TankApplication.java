package com.zgw.tankGame;

import com.zgw.tankFrame.MainFrame;

import java.lang.reflect.Field;
import java.util.Vector;


public class TankApplication {
    public static void main(String[] args) throws Exception {


        MainFrame tankFrame=new MainFrame();
        while (true){

            Thread.sleep(50);
            tankFrame.repaint();
        }

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
