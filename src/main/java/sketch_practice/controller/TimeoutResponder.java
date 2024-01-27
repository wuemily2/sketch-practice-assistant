package sketch_practice.controller;

public interface TimeoutResponder<T> {
    public void respond_to_timeout(T timer);
}
