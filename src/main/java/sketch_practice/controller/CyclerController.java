package sketch_practice.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import sketch_practice.model.ImageCycler;
import sketch_practice.model.ImageFileFinder;
import sketch_practice.model.CountdownTimer;
import sketch_practice.util.CyclerCommand;
import sketch_practice.util.Observer;
import sketch_practice.view.SketchPracticeGUI;

import java.io.File;
import java.util.ArrayList;

/*
Controller class to respond to changes in the model and update the view, as well as
control changes to the model.

In this specific context, it should make calls to image cycler (the model) to cycle to another image.
It does so either in response to updates from a timer (which I will consider part of the model, to represent time left,
which can also change with controller input - start, stop, rewrite) or from users clicking buttons on the view (which
will call the controller to do something).
 */

// Cycler Controller will have no knowledge of the essentially view + controller (event handler) gui component.
public class CyclerController implements TimeoutResponder<CountdownTimer> { //Todo: change from object to timer class
    private CountdownTimer timer;
    private int sketchTime = 300;
    private ImageFileFinder fileFinder;
    private ImageCycler cycler = null; //get output from fileFinder

    //Need to attach view outside of this.
    public CyclerController(ImageFileFinder fileFinder,
                            CountdownTimer timer) {
        this.fileFinder = fileFinder;
        this.timer = timer;
        this.timer.setResponder(this);// Set cycler controller to respond to a timeout
    }
    public CyclerController(ImageFileFinder fileFinder,
                            CountdownTimer timer,
                            int defaultTime) {
        this(fileFinder, timer);
        this.sketchTime = defaultTime;
    }
    // Useful for attaching view. The use of Hashset prevents duplicates probably
    public void attachObserverToModel(Observer o){
        this.fileFinder.attach(o);
        this.timer.attach(o);
        if(cycler != null){
            this.cycler.attach(o);
        }
    }

    public void addFileObject(File fileObject){
        //Add an image or directory to keep track of in fileFinder
        //fileFinder already has error checking
        this.fileFinder.addFileObjects(fileObject);
    }

    public void removeFileObject(File fileObject){
        //Add an image or directory to keep track of in fileFinder
        //fileFinder already has error checking
        this.fileFinder.removeFileObject(fileObject);
    }

    // Class for basic cycler events
    public abstract class CyclerEventHandler implements EventHandler<ActionEvent> {
        CyclerController cc = CyclerController.this;
    }

    public class GoToNextImageEventHandler extends CyclerEventHandler{
        @Override
        public void handle(ActionEvent actionEvent) {
            cc.executeCyclerCommand(CyclerCommand.ADVANCE_NEXT);
        }
    }

    public class GoToPreviousImageEventHandler extends CyclerEventHandler{
        @Override
        public void handle(ActionEvent actionEvent) {
            cc.executeCyclerCommand(CyclerCommand.GO_BACK);
        }
    }

    public class PauseCyclerEventHandler extends CyclerEventHandler{
        @Override
        public void handle(ActionEvent actionEvent) {
            cc.executeCyclerCommand(CyclerCommand.PAUSE_CYCLER);
        }
    }

    public class ContinueCyclerEventHandler extends CyclerEventHandler{
        @Override
        public void handle(ActionEvent actionEvent) {
            cc.executeCyclerCommand(CyclerCommand.CONTINUE_CYCLER);
        }
    }

    private boolean prepSession(Observer o){
        ArrayList<File> allImages = this.fileFinder.getAllImageFilesAsArrayList();
        if(!allImages.isEmpty()){
            this.cycler = new ImageCycler(this.fileFinder.getAllImageFilesAsArrayList());
            this.cycler.attach(o);// Have something listen on the cycler changes.
            return true;
        }
        return false;
    }
    private boolean startSession(Observer o){
        if(this.prepSession(o)){
            // default is false so it sets to true and notifies view that it is visible
            this.cycler.toggleVisibility();// TODO: if we set an initial image in prep, this isn't needed
            this.startCountDownTimer(); // start timer.
            return true;
        }
        return false;
    }

    // Set a new countdown timer
    public void setCountDownTimer(int seconds){
        this.sketchTime = seconds;
        if(this.timer != null){
            this.timer.setNewTime(seconds);
        }
    }

    public void startCountDownTimer(){
        if(this.timer != null){
            this.timer.setNewTime(sketchTime);
            this.timer.startTimer();
        }
    }

    public boolean executeCyclerCommand(CyclerCommand command){
        if (cycler != null){
            switch (command) {
                case ADVANCE_NEXT -> {
                    cycler.go_to_next_image();
                    timer.setNewTime(this.sketchTime);
                    timer.startTimer();
                }
                case GO_BACK -> {
                    cycler.go_to_previous_image();
                    timer.setNewTime(this.sketchTime);
                    timer.startTimer();
                }
                case PAUSE_CYCLER -> timer.stopTimer();
                case CONTINUE_CYCLER -> timer.startTimer();
            }
            return true;
        }
        return false;
    }

    public void respond_to_timeout(CountdownTimer timer){
        this.executeCyclerCommand(CyclerCommand.ADVANCE_NEXT);
    }
}
