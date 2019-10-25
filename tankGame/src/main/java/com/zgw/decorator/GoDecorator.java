package com.zgw.decorator;


import com.zgw.Facede.GameObject;

import java.awt.*;

public  class GoDecorator extends GameObject {
    public GameObject gameObject;
    public GoDecorator(GameObject gameObject){
        this.gameObject=gameObject;
    }
    public void paint(Graphics g) {
        gameObject.paint(g);
    }

}
