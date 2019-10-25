package com.zgw.tankFrame;

import com.zgw.Facede.GameModel;
import com.zgw.factory.BaseExplode;
import com.zgw.resource.ResourceMgr;

import java.awt.*;
import java.util.UUID;

public class Explode extends BaseExplode {

    private static int WIDTH=ResourceMgr.explodes[0].getWidth();
    private static int HEIGHT=ResourceMgr.explodes[0].getHeight();

    private int step=0;
    private int x;
    private int y;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        this.uuid=UUID.randomUUID();
        GameModel.getInstance().addGameObject(this);
    }

    public void paint(Graphics g){
            g.drawImage(ResourceMgr.explodes[step++],x,y,null);
            if(step>=ResourceMgr.explodes.length){
                GameModel.getInstance().removeGameObject(this);
            }
    }
}
