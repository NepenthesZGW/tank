package com.zgw.chain;

import com.zgw.Facede.GameObject;
import com.zgw.factory.BaseTank;
import com.zgw.factory.BaseWall;

import java.awt.*;

public class TankAndWallCollider implements Collider<GameObject>{

    public boolean collision(GameObject t1, GameObject t2) {

        if( (t1 instanceof BaseTank)&& (t2 instanceof BaseWall) ){

            if(t1.getRectangle().intersects(t2.getRectangle())){
                ((BaseTank) t1).back();
               // if(t1.getRectangle().intersects(t2.getRectangle()))
                   // dealCollision(t1.getRectangle(),t2.getRectangle());
                return  true;
            }
        }else if((t1 instanceof BaseWall)&& (t2 instanceof BaseTank)){
               if( collision(t2,t1)) return true;
        }

        return false;
    }

    private  void dealCollision(Rectangle r1,Rectangle r2){


    }
}
