package sketch_practice.controller;

public interface TimeoutResponder<T> {
    void respond_to_timeout(T timer);
}
