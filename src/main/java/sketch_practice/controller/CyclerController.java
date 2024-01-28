package sketch_practice.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import sketch_practice.SketchPractice;
import sketch_practice.model.ImageCycler;
import sketch_practice.model.ImageFileFinder;
import sketch_practice.model.SketchCountdownTimer;
import sketch_practice.util.CyclerCommand;
import sketch_practice.util.Observable;
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
public class CyclerController implements TimeoutResponder<SketchCountdownTimer> { //Todo: change from object to timer class
    private SketchCountdownTimer timer;
    private int sketchTime = 300;
    private ImageFileFinder fileFinder;
    private ImageCycler cycler; //get output from fileFinder
    private SketchPracticeGUI view;

    //Let's do all relational attachments here, except for event handlers
    public CyclerController(SketchPracticeGUI view,
                            ImageFileFinder fileFinder,
                            SketchCountdownTimer timer) {
        this.fileFinder = fileFinder;
        this.timer = timer;
        this.timer.setResponder(this);// Set cycler controller to respond to a timeout
        this.view = view;
        // Add section where controller puts its event handlers onto exposed view action gui
        //TODO: implement here
        // ACTUALLY maybe we should hook these up outside to reduce coupling.

        // Have view observe these model objects
        this.fileFinder.attach(view);
        this.timer.attach(view);
    }
    public CyclerController(SketchPracticeGUI view,
                            ImageFileFinder fileFinder,
                            SketchCountdownTimer timer,
                            int defaultTime) {
        this(view, fileFinder, timer);
        this.sketchTime = defaultTime;
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

    public class AddDirectoryEventHandler extends CyclerEventHandler{
        //TODO figure out how to remove stuff later... via gui
        @Override
        public void handle(ActionEvent actionEvent) {
            DirectoryChooser directoryChooser = new DirectoryChooser(); //May need to use Jfilechooser
            //View must expose the stage for this...
            File selectedDirectory = directoryChooser.showDialog(cc.view.getStage());
            if (selectedDirectory != null) {
                cc.addFileObject(selectedDirectory);
            }
        }
    }

    // If we attach this to some kind of button... it might work???
    public class RemoveFileObjectEventHandler extends CyclerEventHandler{
        File targetFile;

        public RemoveFileObjectEventHandler (File targetFile){
            this.targetFile = targetFile;
        }
        @Override
        public void handle(ActionEvent actionEvent) {
            cc.removeFileObject(this.targetFile);
        }
    }
    private boolean prepSession(){
        ArrayList<File> allImages = this.fileFinder.getAllImageFilesAsArrayList();
        if(!allImages.isEmpty()){
            this.cycler = new ImageCycler(this.fileFinder.getAllImageFilesAsArrayList());
            this.cycler.attach(this.view);// Have view listen to cycler changes
            //TODO add a line here to set initial image of view maybe?
            return true;
        }
        return false;
    }
    public class StartSessionEventHandler extends CyclerEventHandler{
        //Switch scenes and start cycling through images
        @Override
        public void handle(ActionEvent actionEvent) {
            if(cc.prepSession()){
                // default is false so it sets to true and notifies view that it is visible
                this.cc.cycler.toggleVisibility();// TODO: if we set an initial image in prep, this isn't needed
                this.cc.view.switchView();// TODO change if view implementation changes
                this.cc.startCountDownTimer(); // start timer.
            }else{
                //TODO: figure out how to change view text to say image files are empty!
            }

        }
    }

    /*public static synchronized CyclerController getInstance(ImageFileFinder fileFinder,
                                                            SketchCountdownTimer timer) {
        if (instance == null)
            instance = new CyclerController();

        instance.fileFinder = fileFinder;
        instance.timer = timer;

        return instance;
    }*/

    /*public static synchronized CyclerController getInstance() {
        if (instance == null)
            instance = new CyclerController();

        return instance;
    }*/

    // Set a new countdown timer
    public void setCountDownTimer(int seconds){
        this.sketchTime = seconds;
        if(this.timer != null){
            this.timer.setNewTime(seconds);
        }
    }
    /*public SketchCountdownTimer setCountdownTimer(int seconds){
        this.sketchTime = seconds;
        if(this.timer != null){
            this.timer.setNewTime(seconds);
        }else{
            this.timer = new SketchCountdownTimer(seconds, this);
            //this.timer.attach(view); //TODO: check if maybe we want to attach somewhere else.
        }
        return this.timer; //return a reference to this timer, used if needed
    }*/

    public void startCountDownTimer(){
        if(this.timer != null){
            this.timer.setNewTime(sketchTime);
            this.timer.startTimer();
        }
    }

    //FileFinder
    // Set a new filefinder object.
    public void setFileFinder(ImageFileFinder fileFinder){
        this.fileFinder = fileFinder;
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

    public void respond_to_timeout(SketchCountdownTimer timer){
        this.executeCyclerCommand(CyclerCommand.ADVANCE_NEXT);
    }
}
