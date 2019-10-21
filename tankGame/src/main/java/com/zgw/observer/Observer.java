package com.zgw.observer;

import java.io.Serializable;

public interface Observer extends Serializable {
    void dealEvet(GameObjectEvent e);
}
