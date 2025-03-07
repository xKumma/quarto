package be.kdg.integration2.mvpglobal.view.startscreen;

import javafx.animation.*;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class StartScreenTransition extends Transition {

    private final StartScreenView view;
    private int maxDuration;

    RotateTransition rt ;
    RotateTransition rt1 ;
    RotateTransition rt2 ;
    RotateTransition rt3 ;




    public StartScreenTransition(StartScreenView view, int maxDuration) {
        this.view = view;
        this.maxDuration = maxDuration;
        this.setCycleDuration(Duration.seconds(maxDuration));
        this.setCycleCount(1);
        this.setInterpolator(Interpolator.LINEAR);




    }

    public void sqaretransition(Rectangle re1 , Rectangle re2 , Rectangle re3 , Rectangle re4  ) {
        rt= new RotateTransition(Duration.seconds(maxDuration), re1);
        rt.setFromAngle(45);
        rt.setToAngle(360);
        rt.setInterpolator(Interpolator.LINEAR);

        rt1= new RotateTransition(Duration.seconds(maxDuration), re2);
        rt1.setFromAngle(110);
        rt1.setToAngle(360);
        rt1.setInterpolator(Interpolator.LINEAR);
        rt2= new RotateTransition(Duration.seconds(maxDuration), re3);
        rt2.setFromAngle(155);
        rt2.setToAngle(360);
        rt2.setInterpolator(Interpolator.LINEAR);
        rt3= new RotateTransition(Duration.seconds(maxDuration), re4);
        rt3.setFromAngle(0);
        rt3.setToAngle(360);
        rt3.setInterpolator(Interpolator.LINEAR);

        rt.play();
        rt1.play();
        rt2.play();
        rt3.play();


    }

    @Override
    protected void interpolate(double frac) {
        this.view.getTimeDisplay().setStyle(" -fx-text-fill: white ; -fx-font-size: 15px;");
        this.view.getTimeDisplay().setText(String.format("Loading: %.1f", frac * 100));
        this.view.getTimeProgress().setProgress(frac);

        rt.play();
        rt1.play();
        rt2.play();
        rt3.play();


    }
}
