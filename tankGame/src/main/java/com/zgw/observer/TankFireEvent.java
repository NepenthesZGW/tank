package com.zgw.observer;

import com.zgw.Facede.GameObject;

public class TankFireEvent extends GameObjectEvent {

    public TankFireEvent(GameObject source) {
        this.source=source;
    }

    public Object getSource() {
        return source;
    }
}
