package com.zgw.factory;

import com.zgw.tankFrame.*;

public class DefaultFactory extends GameFactory {


    public BaseTank createTank(int x, int y, int width, int height) {
        return new BadTank(x,y,width,height);
    }

    public BaseExplode createExplode(int x, int y) {
        return new Explode(x,y);
    }

    public BaseBullet createBullet(int x, int y, Direct direct, BaseTank tank) {
        return new Bullet(x,y,direct,tank);
    }

    public BaseWall createWall(int x, int y, int width, int height) {
        return new Wall(x,y,width,height);
    }
}
