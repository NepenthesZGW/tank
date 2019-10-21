package com.zgw.strategy;

import com.zgw.Facede.GameModel;
import com.zgw.factory.BaseTank;
import com.zgw.tankFrame.Bullet;
import com.zgw.tankFrame.Direct;
import com.zgw.tankFrame.MainFrame;

import java.awt.*;

public class DefualtFireStrategy implements FireStrategy<BaseTank> {


    public void fire(BaseTank tank) {
        Rectangle rectangle = tank.getRectangle();
        int bx=rectangle.x + (rectangle.width >> 1);
        int by=rectangle.y + (rectangle.height >> 1);
        if(tank.getDirect()==Direct.UP||tank.getDirect()==Direct.DOWN) {
            bx = bx - (Bullet.width >> 1);
            by = by-  (Bullet.height >> 1);
        }else{
            bx = bx - (Bullet.height >> 1);
            by = by - (Bullet.width >> 1);
        }
        GameModel.getInstance().gameFactory.createBullet(bx,by,tank.getDirect(),tank);
    }
}
