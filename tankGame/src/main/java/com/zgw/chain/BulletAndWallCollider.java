package com.zgw.chain;

import com.zgw.Facede.GameObject;
import com.zgw.factory.BaseBullet;
import com.zgw.factory.BaseWall;

public class BulletAndWallCollider implements Collider<GameObject> {
    public boolean collision(GameObject t1, GameObject t2) {

        if((t1 instanceof BaseBullet)&&(t2 instanceof BaseWall) ){
            if(t1.getRectangle().intersects(t2.getRectangle())){
                ((BaseBullet) t1).die();
                return true;
            }
        }else if( (t1 instanceof BaseWall)&&(t2 instanceof BaseBullet) ){
            if(collision(t2,t1)) return true;
        }
        return false;
    }
}
