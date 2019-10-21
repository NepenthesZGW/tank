package com.zgw.tankFrame;

import com.zgw.factory.BaseTank;
import com.zgw.resource.ResourceMgr;
import com.zgw.strategy.FireStrategy;

import java.awt.*;

public class GoodTank extends BaseTank {

    private static final int speed=5;

    private int oldX;
    private int oldY;
    public GoodTank(int x, int y, int width, int height) {
        super();
        oldX=x;
        oldY=y;
        rectangle=new Rectangle(x,y,width,height);
    }

    public void paint(Graphics g){
        switch (direct){
            case UP:
                g.drawImage(ResourceMgr.tankU,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankD,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.tankL,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankR,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
                break;
            default:break;
        }
        move();
    }


    public void back() {
        rectangle.x=oldX;
        rectangle.y=oldY;
    }

    public void die() {

    }

    public void fire(FireStrategy fireStrategy){
           fireStrategy.fire(this);
    }


    public void move(){
        if(!isMoving) return;
        oldX=rectangle.x;
        oldY=rectangle.y;

        if (direct == Direct.UP) rectangle.y -= speed;
        else if (direct == Direct.DOWN) rectangle.y += speed;
        else if (direct == Direct.LEFT) rectangle.x -= speed;
        else if (direct == Direct.RIGHT) rectangle.x += speed;
    }

    public void stop() {
        isMoving=false;
    }

}
