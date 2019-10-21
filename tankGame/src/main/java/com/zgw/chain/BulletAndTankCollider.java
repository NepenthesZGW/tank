package com.zgw.chain;

import com.zgw.Facede.GameObject;
import com.zgw.factory.BaseBullet;
import com.zgw.factory.BaseTank;

public class BulletAndTankCollider implements Collider<GameObject> {


    public boolean collision(GameObject t1, GameObject t2) {
        if((t1 instanceof BaseBullet)&&(t2 instanceof BaseTank)) {
            BaseBullet bullet = (BaseBullet) t1;
            BaseTank tank = (BaseTank) t2;

            if (bullet.getOwner()!=tank&&bullet.getRectangle().intersects(tank.getRectangle())) {
                bullet.die();
                tank.die();
                return  true;
            }
        }else if((t1 instanceof BaseTank)&&(t2 instanceof BaseBullet)){
            if(collision(t2,t1)) return true;
        }
        return false;
    }
}
