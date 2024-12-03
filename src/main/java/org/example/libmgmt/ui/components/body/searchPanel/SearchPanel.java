package org.example.libmgmt.ui.components.body.searchPanel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.style.Style;

import java.util.Objects;

/**
 * A search panel controlling search, filtering and sorting data.
 */
public abstract class SearchPanel {
  protected TextField searchBox;
  protected ComboBox<String> viewOption;
  protected ComboBox<String> sortingOption;
  protected Button clearSearch;
  private final Label viewLabel;
  private final HBox viewContainer;
  private final Label sortbyLabel;
  private final HBox sortbyContainer;
  private final GridPane panel;

  /**
   * Constructor.
   */
  public SearchPanel() {
    searchBox = new TextField();
    viewLabel = new Label("Hiển thị: ");
    viewOption = new ComboBox<>();
    sortbyLabel = new Label("Sắp xếp theo: ");
    sortingOption = new ComboBox<>();
    clearSearch = new Button("Xoá tìm kiếm");
    AnchorPane searchBarWrapper = new AnchorPane(searchBox, clearSearch);
    panel = new GridPane();
    panel.add(searchBarWrapper, 0, 0, 2, 1);
    viewContainer = new HBox(viewLabel, viewOption);
    sortbyContainer = new HBox(sortbyLabel, sortingOption);
    panel.add(viewContainer, 0, 1, 1, 1);
    panel.add(sortbyContainer, 1, 1, 1, 1);
    style();
  }

  /**
   * Sets function for search box, buttons, filter and sort option.
   */
  public abstract void setFunction();

  /**
   * Add filter options.
   */
  public abstract void addViewOption();

  /**
   * Add sorting option.
   */
  public abstract void addSortOption();

  public String getViewOption() {
    return viewOption.getValue();
  }

  public String getSortingOption() {
    return sortingOption.getValue();
  }

  private void style() {
    double iconSize = 24;
    Image searchIcon = new Image(Objects.requireNonNull(LibMgmt.class
        .getResourceAsStream("img/search.png")));
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
    Style.styleRoundedSolidButton(clearSearch, Style.LIGHTGREEN, 100, 30, 12);
    AnchorPane.setLeftAnchor(searchBox, 0.0);
    AnchorPane.setRightAnchor(searchBox, 0.0);
    AnchorPane.setTopAnchor(searchBox, 0.0);
    AnchorPane.setBottomAnchor(searchBox, 0.0);

    AnchorPane.setRightAnchor(clearSearch, 10.0);
    AnchorPane.setTopAnchor(clearSearch, 10.0);
    AnchorPane.setBottomAnchor(clearSearch, 10.0);
    Font labelFont = Font.font("Inter", 14);
    viewLabel.setFont(labelFont);
    sortbyLabel.setFont(labelFont);

    viewOption.setBackground(Background.EMPTY);
    viewOption.setStyle("-fx-font: 14 Inter");
    sortingOption.setBackground(Background.EMPTY);
    sortingOption.setStyle("-fx-font: 14 Inter");

    viewContainer.setAlignment(Pos.CENTER_LEFT);
    sortbyContainer.setAlignment(Pos.CENTER_LEFT);

    ColumnConstraints col1 = new ColumnConstraints();
    ColumnConstraints col2 = new ColumnConstraints();
    col1.setPercentWidth(40);
    col2.setPercentWidth(60);
    panel.getColumnConstraints().addAll(col1, col2);

    panel.setVgap(10);
    panel.setHgap(10);
    panel.setMaxHeight(Region.USE_PREF_SIZE);
  }

  public GridPane getPanel() {
    viewOption.getSelectionModel().selectFirst();
    sortingOption.getSelectionModel().selectFirst();
    return panel;
  }
}
