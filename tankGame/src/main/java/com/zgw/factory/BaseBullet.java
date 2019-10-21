package com.zgw.factory;

import com.zgw.Facede.GameObject;
import com.zgw.tankFrame.Direct;

import java.awt.*;

public abstract class BaseBullet extends GameObject {
    protected Direct direct;
    protected BaseTank owner;

    public abstract void die();
    public abstract void paint(Graphics g);
    public BaseTank getOwner() { return owner; }
}
