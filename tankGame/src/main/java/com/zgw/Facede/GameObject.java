package com.zgw.Facede;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

public abstract class GameObject implements Serializable {
    protected Rectangle rectangle;
    public Rectangle getRectangle() {
        return rectangle;
    }
    public abstract void paint(Graphics g);
    protected UUID uuid=null;
    public UUID getUuid() {
        return uuid;
    }
}
