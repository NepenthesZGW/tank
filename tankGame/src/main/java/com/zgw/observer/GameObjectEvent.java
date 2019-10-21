package com.zgw.observer;

import com.zgw.Facede.GameObject;

public abstract class GameObjectEvent {

    protected GameObject source;
    public abstract Object getSource();
}
