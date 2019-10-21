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
                if(t1.getRectangle().intersects(t2.getRectangle()))
                    dealCollision(t1.getRectangle(),t2.getRectangle());
                return  true;
            }
        }else if((t1 instanceof BaseWall)&& (t2 instanceof BaseTank)){
               if( collision(t2,t1)) return true;
        }

        return false;
    }

    private  void dealCollision(Rectangle r1,Rectangle r2){
        if(Math.abs(r1.x-r2.x)<Math.abs(r1.y-r2.y)) {

            if(r1.y>r2.y){
                r1.y=r2.y+r2.height;
            }else{
                r1.y=r2.y-r1.height;
            }
        }else{
            if(r1.x>r2.x){
                r1.x=r2.x+r2.width;
            }else{
                r1.x=r2.x-r1.width;
            }
        }
    }
}
