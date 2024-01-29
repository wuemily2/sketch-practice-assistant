package sketch_practice.model;

import sketch_practice.controller.TimeoutResponder;
import sketch_practice.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/*
This timer is used as a simple countdown timer.
 */
public class CountdownTimer extends Observable {
    Timer timer = new Timer(); //A timer object to subtract one from timeLeft every second
    private int timeLeft = 300; // Time left, in seconds, in the countdown
    TimeoutResponder<CountdownTimer> responder;
    private boolean isTimerRunning = false;

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

    public String getTimeLeftAsString() {// just minutes and seconds cuz you shouldn't use this if you're hitting 1 hr
        return String.format("%d:%02d", this.timeLeft / 60, this.timeLeft % 60 );
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
                CountdownTimer.this.stopTimer();// Stop the timer at 0. Don't call after responder
                // because the responder may want to start a new timer
                if(responder != null) {
                    CountdownTimer.this.responder.respond_to_timeout(CountdownTimer.this);
                }

            }
        }
        public void run(){
            executeTimerTick();
        }
    }

    public void stopTimer(){ //Need to reschedule task to start again.
        this.timer.cancel();
        this.isTimerRunning = false;
        this.notifyObservers();
    }
    protected void assignTimerTask(){// Called only in start timer
        this.timer = new Timer(); // Need a new timer because you can't schedule a task on an already used one
        this.timer.scheduleAtFixedRate(new TickingTask(), 1000, 1000);
    }
    public void startTimer(){
        this.stopTimer(); // Ensure timer is stopped so we don't schedule another one
        this.assignTimerTask();
        this.isTimerRunning = true;
        this.notifyObservers();
    }

    public void timerPlayToggle(){
        if(this.isTimerRunning){
            this.stopTimer();
        }else{
            this.startTimer();
        }
    }

    public boolean getIfTimerisRunning(){
        return this.isTimerRunning;
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
