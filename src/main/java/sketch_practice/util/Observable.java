package sketch_practice.util;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

public class Observable {
    private HashSet<Observer> observers = new HashSet<>(); // prevent duplicate observers.
    public void attach(Observer o) {
        observers.add(o);
    }
    public void detach(Observer o) {
        observers.remove(o);
    }
    public void notifyObservers() {
        for(Observer o:observers) {
            o.update(this);
        }
    }
}
