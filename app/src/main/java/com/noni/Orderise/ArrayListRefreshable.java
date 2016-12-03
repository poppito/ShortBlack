package com.noni.Orderise;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.RandomAccess;

public class ArrayListRefreshable<E> extends ArrayList implements Serializable, Cloneable, RandomAccess {

    public ArrayListRefreshListener mListener;

    public ArrayListRefreshable() {
        super();
    }

    public ArrayListRefreshable(Collection collection) {
        super(collection);
    }

    public void setArrayListRefreshListener(ArrayListRefreshListener refreshListener) {
        this.mListener = refreshListener;
    }
}
