package com.zgw.observer;

import com.zgw.factory.BaseTank;
import com.zgw.strategy.DefualtFireStrategy;

public class FireObserver implements Observer {
    public void dealEvet(GameObjectEvent e) {
        Object source = e.getSource();
        if(source instanceof BaseTank){
            ((BaseTank) source).fire(new DefualtFireStrategy());
        }
    }
}
