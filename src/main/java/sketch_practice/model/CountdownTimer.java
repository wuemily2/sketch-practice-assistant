package sketch_practice.model;

import sketch_practice.controller.TimeoutResponder;
import sketch_practice.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/*
This timer is used as a simple countdown timer.
 */
public class CountdownTimer extends Observable {
    Timer timer; //A timer object to subtract one from timeLeft every second
    private int timeLeft = 300; // Time left, in seconds, in the countdown
    TimeoutResponder<CountdownTimer> responder;

    public CountdownTimer(){
        this.responder = null;
    }
    public CountdownTimer(int seconds){
        this();
        this.timeLeft = seconds;
    }

    public CountdownTimer(TimeoutResponder<CountdownTimer> responder){
        this.responder = responder;
    }

    public CountdownTimer(int seconds, TimeoutResponder<CountdownTimer> responder){
        this.timeLeft = seconds;
        this.responder = responder;
    }

    public void setResponder(TimeoutResponder<CountdownTimer> responder) {
        this.responder = responder;
    }

    public int getTimeLeft(){// In seconds
        return this.timeLeft;
    }

    // TODO: seconds to two digit placements
    public String getTimeLeftAsString() {// just minutes and seconds cuz you shouldn't use this if you're hitting 1 hr
        return String.format("%d:%d", this.timeLeft / 60, this.timeLeft % 60 );
    }

    class TickingTask extends TimerTask { // Should be scheduled to run every second
        public void executeTimerTick(){
            //System.out.println("Executing tick.");
            //System.out.format("TimeLeft %d", timeLeft);
            if (CountdownTimer.this.timeLeft > 0) {

                CountdownTimer.this.timeLeft--;
                CountdownTimer.this.notifyObservers();
            }
            if(CountdownTimer.this.timeLeft <= 0){
                if(responder != null) {
                    CountdownTimer.this.responder.respond_to_timeout(CountdownTimer.this);
                }
                CountdownTimer.this.stopTimer();// Stop the timer at 0
            }
        }
        public void run(){
            executeTimerTick();
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
        TimeoutResponder<CountdownTimer> responder = new TimeoutResponder<CountdownTimer>() {
            @Override
            public void respond_to_timeout(CountdownTimer timer) {
                System.out.println("Timeout happened!");
            }
        };
        CountdownTimer timer = new CountdownTimer(5, responder);
        timer.startTimer();
        timer.setNewTime(10);
        timer.startTimer();

    }

}
