package com.zgw.Facede;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
    protected Rectangle rectangle;
    public Rectangle getRectangle() {
        return rectangle;
    }
    public abstract void paint(Graphics g);
}
