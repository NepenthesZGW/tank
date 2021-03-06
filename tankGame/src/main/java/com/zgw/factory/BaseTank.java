package com.zgw.factory;

import com.zgw.Facede.GameObject;
import com.zgw.strategy.FireStrategy;
import com.zgw.tankFrame.Direct;

import java.awt.*;
import java.util.UUID;

public abstract class BaseTank extends GameObject {
    protected Direct direct;
    protected BaseTank(){
        direct=Direct.UP;
    }
    public boolean isMoving=false;
    public boolean isMoving() {
        return isMoving;
    }
    public Direct getDirect() {
        return direct;
    }



    public void setDirect(Direct direct)
    {
        isMoving=true;
        this.direct = direct;
    }
    public abstract void back();
    public abstract void die();

    public abstract void fire(FireStrategy fireStrategy);


    public abstract void move();
    public abstract void stop();
}
