package sketch_practice.model;

import javafx.application.Platform;
import sketch_practice.controller.TimeoutResponder;

import java.util.Timer;


//Extension of SketchCountdownTimer to run on specifically the JavaFX application thread
public class JavaFXCountdownTimer extends CountdownTimer {
    public JavaFXCountdownTimer(){
        super();
    }
    public JavaFXCountdownTimer(int seconds){
        super(seconds);
    }

    public JavaFXCountdownTimer(TimeoutResponder<CountdownTimer> responder){
        super(responder);
    }

    public JavaFXCountdownTimer(int seconds, TimeoutResponder<CountdownTimer> responder){
        super(seconds, responder);
    }
    class JavaFXTickingTask extends TickingTask{ // Should be scheduled to run every second
        @Override
        public void run(){
            Platform.runLater(() -> executeTimerTick());
        }
    }

    @Override
    protected void assignTimerTask(){
        this.timer = new Timer(); // Need a new timer because you can't schedule a task on an already used one
        this.timer.scheduleAtFixedRate(new JavaFXTickingTask(), 1000, 1000);
    }

}
