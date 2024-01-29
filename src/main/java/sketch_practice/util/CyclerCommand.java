package sketch_practice.util;

public enum CyclerCommand {
    // Go to the image cycler scene or exit out of it
    START_CYCLING, EXIT_CYCLING,
    // Go to the next image in the cycler, or the previous
    ADVANCE_NEXT, GO_BACK,
    // Pause the timer on the current image, or restart it
    TOGGLE_PAUSE_OR_PLAY

}
