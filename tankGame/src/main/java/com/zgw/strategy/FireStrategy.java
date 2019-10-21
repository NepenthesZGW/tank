package com.zgw.strategy;

import java.io.Serializable;

public interface FireStrategy<T> {
    void fire(T t);
}
