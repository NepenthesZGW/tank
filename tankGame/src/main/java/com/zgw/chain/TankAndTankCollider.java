package com.zgw.chain;

import com.zgw.Facede.GameObject;
import com.zgw.factory.BaseTank;

import java.awt.*;

public class TankAndTankCollider implements Collider<GameObject> {


    public boolean collision(GameObject t1, GameObject t2) {
        if((t1 instanceof BaseTank)&&(t2 instanceof BaseTank)){
            Rectangle r1=t1.getRectangle();
            Rectangle r2=t2.getRectangle();
           if(r1.intersects(r2)){
               ((BaseTank) t1).back();
               ((BaseTank) t2).back();
               if(r1.intersects(r2)) deal(r1,r2);
               return true;
           }
        }
        return false;
    }
    private void deal(Rectangle r1,Rectangle r2){

        if(Math.abs(r1.x-r2.x)<Math.abs(r1.y-r2.y)){

            int temp=r1.y;

            if(r1.y>r2.y){
                int s=(r2.y+r2.height-r1.y)>>1;
                r1.y=r2.y+r2.height-s;
                r2.y=temp-r2.height+s;
            }
            if(r1.y<r2.y){
                int s=(r1.y+r1.height-r2.y)>>1;
                r1.y=r2.y-r1.height+s;
                r2.y=temp+r1.height-s;
            }
        }else{

            int temp=r1.x;
            if(r1.x>r2.x){
                int s=(r2.x+r2.width-r1.x)>>1;
                r1.x=r2.x+r2.width-s;
                r2.x=temp-r2.width+s;
            }
            if(r1.x<r2.x){
                int s=(r1.x+r1.width-r2.x)>>1;
                r1.x=r2.x-r1.width+s;
                r2.x=temp+r1.height-s;
            }
        }

    }
}
