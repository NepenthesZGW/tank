package com.zgw.strategy;

import com.zgw.Facede.GameModel;
import com.zgw.factory.BaseTank;
import com.zgw.tankFrame.Bullet;
import com.zgw.tankFrame.Direct;
import com.zgw.tankFrame.MainFrame;

import java.awt.*;

public class FourDirFireStrategy implements FireStrategy<BaseTank>{

    public void fire(BaseTank tank) {
        Rectangle rectangle = tank.getRectangle();
        int bx = rectangle.x + (rectangle.width >> 1);
        int by = rectangle.y + (rectangle.height >> 1);
        GameModel.getInstance().gameFactory.createBullet(bx - (Bullet.width >> 1),by-(Bullet.height >> 1),Direct.UP,tank);
        GameModel.getInstance().gameFactory.createBullet(bx - (Bullet.width >> 1),by-(Bullet.height >> 1),Direct.DOWN,tank);
        GameModel.getInstance().gameFactory.createBullet(bx- (Bullet.height >> 1),by- (Bullet.width >> 1),Direct.LEFT,tank);
        GameModel.getInstance().gameFactory.createBullet(bx- (Bullet.height >> 1),by- (Bullet.width >> 1),Direct.RIGHT,tank);
    }
}
