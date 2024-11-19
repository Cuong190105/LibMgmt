package org.example.libmgmt.ui.style;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class StyleNavBar {
    public static final double BTN_MAX_WIDTH = 100;
    public static final double BTN_HEIGHT = 50;
    public static final double RADIUS = 15;

    public static void styleButton(Button btn) {
        Style.styleRoundedButton(btn, BTN_MAX_WIDTH, BTN_HEIGHT);
        Style.styleHoverEffect(btn);
        HBox.setHgrow(btn, Priority.ALWAYS);
//        Style.setDebugBorder(btn);
    }
}
