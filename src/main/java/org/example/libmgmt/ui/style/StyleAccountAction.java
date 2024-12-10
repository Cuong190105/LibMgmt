package org.example.libmgmt.ui.style;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class StyleAccountAction {
    private static final double SIZE = 50;
    private static final Background BG = new Background(new BackgroundFill(
            Style.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY
    ));

    public static void styleButton(Button btn, boolean hasBackground) {
        if (hasBackground) {
            Style.styleCircleButton(btn, SIZE);
        } else {
            Style.styleCircleButton(btn, SIZE, BG);
        }
        Style.styleHoverEffect(btn);
    }
}
