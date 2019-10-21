package com.zgw.decorator;

import com.zgw.Facede.GameObject;

import java.awt.*;

public class RectDecorator extends GoDecorator {

    public RectDecorator(GameObject gameObject) {
        super(gameObject);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Color color = g.getColor();
        g.setColor(Color.YELLOW);
        Rectangle rectangle = gameObject.getRectangle();
        g.drawRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
        g.setColor(color);
    }
}
