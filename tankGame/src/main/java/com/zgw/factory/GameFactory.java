package com.zgw.factory;

import com.zgw.tankFrame.Direct;

import java.io.Serializable;

public abstract class GameFactory {
   public abstract BaseTank createTank(int x, int y, int width, int height);
   public abstract BaseExplode createExplode(int x, int y);
   public abstract BaseBullet createBullet(int x, int y, Direct direct, BaseTank tank);
   public abstract BaseWall createWall(int x, int y,int width,int height);
}
