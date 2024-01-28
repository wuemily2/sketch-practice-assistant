package sketch_practice.model;

import sketch_practice.controller.TimeoutResponder;
import sketch_practice.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/*
This timer is used as a simple countdown timer.
 */
public class SketchCountdownTimer extends Observable {
    private Timer timer; //A timer object to subtract one from timeLeft every second
    private int timeLeft = 300; // Time left, in seconds, in the countdown
    TimeoutResponder<SketchCountdownTimer> responder;

    public SketchCountdownTimer(){
        this.responder = null;
    }
    public SketchCountdownTimer(int seconds){
        this();
        this.timeLeft = seconds;
    }

    public SketchCountdownTimer(TimeoutResponder<SketchCountdownTimer> responder){
        this.responder = responder;
    }

    public SketchCountdownTimer(int seconds, TimeoutResponder<SketchCountdownTimer> responder){
        this.timeLeft = seconds;
        this.responder = responder;
    }

    public void setResponder(TimeoutResponder<SketchCountdownTimer> responder) {
        this.responder = responder;
    }

    public int getTimeLeft(){// In seconds
        return this.timeLeft;
    }

    public String getTimeLeftAsString() {// just minutes and seconds cuz you shouldn't use this if you're hitting 1 hr
        return String.format("%d:%d", this.timeLeft / 60, this.timeLeft % 60 );
    }

    class TickingTask extends TimerTask { // Should be scheduled to run every second
        public void run(){
            if (SketchCountdownTimer.this.timeLeft > 0) {
                SketchCountdownTimer.this.timeLeft--;
                SketchCountdownTimer.this.notifyObservers();
            }
            if(SketchCountdownTimer.this.timeLeft <= 0){
                if(responder != null) {
                    SketchCountdownTimer.this.responder.respond_to_timeout(SketchCountdownTimer.this);
                }
                SketchCountdownTimer.this.stopTimer();// Stop the timer at 0
            }
        }
    }
    public void startTimer(){
        this.timer = new Timer(); // Need a new timer because you can't schedule a task on an already used one
        this.timer.scheduleAtFixedRate(new TickingTask(), 1000, 1000);
    }

    public void stopTimer(){ //Need to reschedule task to start again.
        this.timer.cancel();
    }

    public void setNewTime(int newTimeInSeconds){
        // Stop the timer and assigns a new time. DOES NOT start the timer.
        this.stopTimer();
        this.timeLeft = newTimeInSeconds;
        this.notifyObservers(); // notify a change in displayed time
    }

    public static void main(String[]args){
        TimeoutResponder<SketchCountdownTimer> responder = new TimeoutResponder<SketchCountdownTimer>() {
            @Override
            public void respond_to_timeout(SketchCountdownTimer timer) {
                System.out.println("Timeout happened!");
            }
        };
        SketchCountdownTimer timer = new SketchCountdownTimer(5, responder);
        timer.startTimer();
        timer.setNewTime(10);
        timer.startTimer();
        timer.stopTimer();
        timer.stopTimer(); // multipler stops do not cause errors.

    }

}
