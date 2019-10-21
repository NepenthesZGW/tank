package com.zgw.resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class ResourceMgr {
    public static BufferedImage tankU,tankD,tankL,tankR;
    public static BufferedImage bulletU,bulletD,bulletL,bulletR;
    public static BufferedImage[] explodes=new BufferedImage[16];
    static {
        try {
            tankU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.png"));
            tankD=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.png"));
            tankL=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.png"));
            tankR=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.png"));
            bulletU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletD=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.png"));
            bulletL=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.png"));
            bulletR=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.png"));
            for (int i = 0; i <16 ; i++) {
                explodes[i]=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/explode/e"+(i+1)+".gif"));
            }
            System.out.println("--------------is ready--------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
