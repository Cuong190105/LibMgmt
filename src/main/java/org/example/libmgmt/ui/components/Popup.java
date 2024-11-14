package org.example.libmgmt.ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.libmgmt.ui.style.Style;

import java.util.List;

public class Popup {
    private Label title;
    private Text body;
    private TextField response;
    private HBox btnList;

    public Popup(String title, String body) {
        this.title = new Label(title);
        this.body = new Text(body);
        btnList = new HBox();
        btnList.setSpacing(10);
    }

    public void addRespondField(String prompt) {
        response = new TextField();
        response.setPromptText(prompt);
    }

    public void addOkBtn() {
        Button okBtn = new Button("OK");
        Style.styleRoundedButton(okBtn, 250, 50);
        HBox.setHgrow(okBtn, Priority.ALWAYS);
        btnList.getChildren().add(okBtn);
    }

    public void addCancelBtn() {
        Button okBtn = new Button("HỦY");
        Style.styleRoundedButton(okBtn, 250, 50);
        HBox.setHgrow(okBtn, Priority.ALWAYS);
        btnList.getChildren().add(okBtn);
    }

    public void addCustomBtn(String text, Color color) {
        Button btn = new Button(text);
        Style.styleRoundedButton(btn, 250, 50);
        btn.getBackground().getFills().get(0).getFill();
        HBox.setHgrow(btn, Priority.ALWAYS);
        btnList.getChildren().add(btn);
    }

    public VBox getPopup() {
        VBox container = new VBox(title, body, btnList);
        container.setPrefSize(400, 300);
        return container;
    }
}
