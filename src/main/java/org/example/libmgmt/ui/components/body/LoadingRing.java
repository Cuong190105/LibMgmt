package org.example.libmgmt.ui.components.body;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.util.Duration;
import org.example.libmgmt.ui.style.Style;

/**
 * A loading animation.
 */
public class LoadingRing {
  private final Pane wrapper;
  private final Arc ring;
  private final Timeline animation;

  public LoadingRing() {
    ring = new Arc(100, 100, 90, 90, 90, 270);
    ring.setStrokeWidth(10);
    ring.setStroke(Style.LIGHTGREEN);
    ring.setFill(Color.TRANSPARENT);
    wrapper = new Pane(ring);
    wrapper.setMaxSize(200, 200);
    animation = new Timeline();
    animation.setCycleCount(Animation.INDEFINITE);
    KeyFrame kf = new KeyFrame(Duration.seconds(1), new KeyValue(
        wrapper.rotateProperty(), wrapper.getRotate() + 360
    ));
    animation.getKeyFrames().add(kf);
  }

  public Pane getRing() {
    animation.play();
    return wrapper;
  }
}
