package be.kdg.integration2.mvpglobal.view.startscreen;

import javafx.animation.*;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
/**
 * Animates the nodes created in the View Start screen
 */
public class StartScreenTransition extends Transition {

    private final StartScreenView view;
    private int maxDuration;
    private RotateTransition rt , rt1 , rt2 , rt3  ;

    public StartScreenTransition(StartScreenView view, int maxDuration) {
        this.view = view;
        this.maxDuration = maxDuration;
        this.setCycleDuration(Duration.seconds(maxDuration));
        this.setCycleCount(1);
        this.setInterpolator(Interpolator.LINEAR);

    }

    public void sqaretransition(Rectangle re1 , Rectangle re2 , Rectangle re3 , Rectangle re4  ) {

        transition(re1 , rt , 0);
        transition(re2 , rt1 , 45);
        transition(re3 , rt2 , 110);
        transition(re4 , rt3 , 155);



    }
    /**
     * set duration , starting point and ending point of the RotateTransition obj for every node passed as parameter
     */
    public void transition(Rectangle rn , RotateTransition rtn , double fromAngle){
        rtn = new RotateTransition(Duration.seconds(maxDuration), rn);
        rtn.setInterpolator(Interpolator.LINEAR);
        rtn.setFromAngle(fromAngle);
        rtn.setToAngle(360);
        rtn.play();
    }

    @Override
    protected void interpolate(double frac)
    {
        this.view.getTimeDisplay().setStyle(" -fx-text-fill: white ; -fx-font-size: 15px;");
        this.view.getTimeDisplay().setText(String.format("Loading: %.1f", frac * 100));
        this.view.getTimeProgress().setProgress(frac);

    }



}
