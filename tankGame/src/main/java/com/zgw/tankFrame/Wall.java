package com.zgw.tankFrame;

import com.zgw.Facede.GameModel;
import com.zgw.factory.BaseWall;

import java.awt.*;
import java.util.UUID;

public class Wall  extends BaseWall {


    public Wall(int x,int y,int width,int height){
        this.rectangle=new Rectangle(x,y,width,height);
        this.uuid=UUID.randomUUID();
        GameModel.getInstance().addGameObject(this);
    }



    public void paint(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
        g.setColor(oldColor);
    }
}
