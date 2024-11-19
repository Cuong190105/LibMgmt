package org.example.libmgmt.ui.components.body;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.style.Style;

public class SearchPanel {
    private TextField searchBox;
    private Label viewLabel;
    private ComboBox<String> viewOption;
    private HBox viewContainer;
    private Label sortbyLabel;
    private ComboBox<String> sortingOption;
    private HBox sortbyContainer;
    private GridPane panel;

    public SearchPanel() {
        searchBox = new TextField();
        viewLabel = new Label("Hiển thị: ");
        viewOption = new ComboBox<>();
        sortbyLabel = new Label("Sắp xếp theo: ");
        sortingOption = new ComboBox<>();
        panel = new GridPane();
        panel.add(searchBox, 0, 0, 2, 1);
        viewContainer = new HBox(viewLabel, viewOption);
        sortbyContainer = new HBox(sortbyLabel, sortingOption);
        panel.add(viewContainer, 0, 1, 1, 1);
        panel.add(sortbyContainer, 1, 1, 1, 1);
        style();
    }

    public void addViewOption(String... option) {
        viewOption.getItems().addAll(option);
    }

    public void addSortOption(String... option) {
        sortingOption.getItems().addAll(option);
    }

    public String getViewOption() {
        return viewOption.getValue();
    }

    public String getSortingOption() {
        return sortingOption.getValue();
    }

    private void style() {
        double iconSize = 24;
        Image searchIcon = new Image(LibMgmt.class.getResourceAsStream("img/search.png"));
        searchBox.setBackground(new Background(new BackgroundImage(
                searchIcon,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, (50 - iconSize) / 2, false,
                        Side.TOP, (50 - iconSize) / 2, false),
                new BackgroundSize(iconSize, iconSize, false, false, false, false))
        ));

        Style.styleTextField(searchBox, Double.MAX_VALUE, 50, 24, "Tìm kiếm");
        searchBox.setBorder(new Border(new BorderStroke(
                Color.GRAY, BorderStrokeStyle.SOLID,
                Style.SMALL_CORNER, BorderWidths.DEFAULT
        )));
        searchBox.setPadding(new Insets(0, 0, 0, 50));

        Font labelFont = Font.font("Inter", 16);
        viewLabel.setFont(labelFont);
        sortbyLabel.setFont(labelFont);

        viewOption.setBackground(Background.EMPTY);
        viewOption.setStyle("-fx-font: 16 Inter");
        sortingOption.setBackground(Background.EMPTY);
        sortingOption.setStyle("-fx-font: 16 Inter");

        viewContainer.setAlignment(Pos.CENTER_LEFT);
        sortbyContainer.setAlignment(Pos.CENTER_LEFT);

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        panel.getColumnConstraints().addAll(col1, col2);

        panel.setVgap(10);
        panel.setHgap(10);
        panel.setMaxHeight(Region.USE_PREF_SIZE);
        panel.setPadding(new Insets(25));
    }

    public GridPane getPanel() {
        viewOption.getSelectionModel().selectFirst();
        sortingOption.getSelectionModel().selectFirst();
        return panel;
    }
}
