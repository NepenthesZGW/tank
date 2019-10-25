package com.zgw.strategy;

import com.zgw.Facede.GameModel;
import com.zgw.encoder.BulletMsg;
import com.zgw.factory.BaseBullet;
import com.zgw.factory.BaseTank;
import com.zgw.netty.TankClient;
import com.zgw.tankFrame.Bullet;
import com.zgw.tankFrame.Direct;
import com.zgw.tankFrame.MainFrame;

import java.awt.*;

public class FourDirFireStrategy implements FireStrategy<BaseTank>{

    public void fire(BaseTank tank) {
        Rectangle rectangle = tank.getRectangle();
        int bx = rectangle.x + (rectangle.width >> 1);
        int by = rectangle.y + (rectangle.height >> 1);
        BaseBullet bullet = GameModel.getInstance().gameFactory.createBullet(bx - (Bullet.width >> 1), by - (Bullet.height >> 1), Direct.UP, tank);
        TankClient.INSTANCE.send(new BulletMsg(bullet));
        bullet= GameModel.getInstance().gameFactory.createBullet(bx - (Bullet.width >> 1),by-(Bullet.height >> 1),Direct.DOWN,tank);
        TankClient.INSTANCE.send(new BulletMsg(bullet));
        bullet=  GameModel.getInstance().gameFactory.createBullet(bx- (Bullet.height >> 1),by- (Bullet.width >> 1),Direct.LEFT,tank);
        TankClient.INSTANCE.send(new BulletMsg(bullet));
        bullet=  GameModel.getInstance().gameFactory.createBullet(bx- (Bullet.height >> 1),by- (Bullet.width >> 1),Direct.RIGHT,tank);
        TankClient.INSTANCE.send(new BulletMsg(bullet));

    }
}
