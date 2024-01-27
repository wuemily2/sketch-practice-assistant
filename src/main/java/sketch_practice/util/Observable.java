package sketch_practice.util;

import java.util.ArrayList;


/*
TODO: replace with earlier implementation because Observable is deprecated
 */
public class Observable extends java.util.Observable {

    @Override
    public void notifyObservers() {
        this.setChanged();
        super.notifyObservers();
    }
}
