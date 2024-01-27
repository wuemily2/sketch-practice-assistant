package sketch_practice.util;

import java.util.ArrayList;


/*
We'll use this in conjunction with java.util.Observer
 */
public class Observable extends java.util.Observable {

    @Override
    public void notifyObservers() {
        this.setChanged();
        super.notifyObservers();
    }
}
