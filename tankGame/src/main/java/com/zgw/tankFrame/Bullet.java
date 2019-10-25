package com.zgw.tankFrame;

import com.zgw.Facede.GameModel;
import com.zgw.encoder.BulletMsg;
import com.zgw.factory.BaseBullet;
import com.zgw.factory.BaseTank;
import com.zgw.resource.ResourceMgr;

import java.awt.*;
import java.util.UUID;

public class Bullet extends BaseBullet {
    public static final int speed=10;
    public static final int width=10;
    public static final int height=30;
    public Bullet(int x, int y, Direct direct,BaseTank owner) {
        rectangle=new Rectangle(x,y,width,height);
        this.direct = direct;
        this.owner  = owner;
        this.uuid=UUID.randomUUID();
        GameModel.getInstance().addGameObject(this);
    }

    public Bullet(BulletMsg bulletMsg) {
        rectangle=new Rectangle(bulletMsg.x,bulletMsg.y,width,height);
        this.direct = bulletMsg.direct;
        this.uuid=bulletMsg.uuid;
        this.owner  = (BaseTank)GameModel.getInstance().tankMap.get(bulletMsg.owner);
        GameModel.getInstance().addGameObject(this);
    }


    public void paint(Graphics g){

        switch (direct){
            case UP:
                g.drawImage(ResourceMgr.bulletU,rectangle.x,rectangle.y,width,height,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD,rectangle.x,rectangle.y,width,height,null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.bulletL,rectangle.x,rectangle.y,height,width,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR,rectangle.x,rectangle.y,height,width,null);
                break;
            default:break;
        }
        move();
    }

    public void move(){
        if (direct == Direct.UP) rectangle.y -= speed;
        if (direct == Direct.DOWN) rectangle.y += speed;
        if (direct == Direct.LEFT) rectangle.x -= speed;
        if (direct == Direct.RIGHT) rectangle.x += speed;
        if(!rectangle.intersects(MainFrame.rectangle)) die();
    }

    public void die(){
        GameModel.getInstance().removeGameObject(this);
    }

}
