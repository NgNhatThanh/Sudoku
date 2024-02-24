package sudoku;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TimeLabel extends Label {
    private int mins = 0, sec = 0;
    TimeLabel(){
        tick.setCycleCount(Animation.INDEFINITE);
        setTimeDisplay();
    }

    Timeline tick = new Timeline(new KeyFrame(Duration.seconds(1), (event) ->{
        ++sec;
        if(sec == 60){
            ++mins;
            sec = 0;
        }
        setTimeDisplay();
    }));

    private void setTimeDisplay(){
        setText(String.format("%02d:%02d", mins, sec));
    }

    public void startTime(){
        tick.play();
    }

    public void pauseTime(){
        tick.pause();
    }
    public void reset(){mins = 0; sec = 0;}
}
