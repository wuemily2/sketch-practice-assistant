package sketch_practice.controller;

import sketch_practice.util.Observable;
import sketch_practice.util.Observer;

/*
Controller class to respond to changes in the model and update the view, as well as
control changes to the model.

In this specific context, it should make calls to image cycler (the model) to cycle to another image.
It does so either in response to updates from a timer (which I will consider part of the model, to represent time left,
which can also change with controller input - start, stop, rewrite) or from users clicking buttons on the view (which
will call the controller to do something).
 */
public class CyclerController implements TimeoutResponder<Object> { //Todo: change from object to timer class

    public void respond_to_timeout(Object o){

    }
}
