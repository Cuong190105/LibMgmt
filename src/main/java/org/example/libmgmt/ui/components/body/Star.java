package org.example.libmgmt.ui.components.body;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import org.example.libmgmt.ui.style.Style;

import static javafx.scene.paint.Color.BLACK;

public class Star {
    public static final double[] vertices = {
            10, 0,
            12.06, 6.34,
            20, 6.34,
            13.74, 11.52,
            16.5, 20,
            10, 14.62,
            3.5, 20,
            6.26, 11.52,
            0, 6.34,
            7.94, 6.34
    };

    /**
     * Get a block of 5 rating stars with custom size. The default star's size (1x scaling) is 20px by 20px.
     * @param scale The scaling ratio.
     * @return The rating stars block.
     */
    public static HBox getStar(double scale, double rating) {
        HBox ratingStar = new HBox();
        for (int i = 0; i < 5; i++) {
            Polygon star = new Polygon();
            for (double d : vertices) {
                star.getPoints().add(d * scale);
            }
            double tmp;
            if (rating - i <= 0) {
                tmp = 0;
            } else if (rating - i >= 1) {
                tmp = 1;
            } else {
                tmp = rating - i;
            }
            Stop[] s = new Stop[]{new Stop(0, Style.DARKGREEN), new Stop(tmp, Style.DARKGREEN),
                    new Stop(tmp, Color.TRANSPARENT), new Stop(1, Color.TRANSPARENT)};
            LinearGradient ln = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, s);
            star.setFill(ln);
            star.setStroke(BLACK);
            ratingStar.getChildren().add(star);
        }
        return ratingStar;
    }
}
