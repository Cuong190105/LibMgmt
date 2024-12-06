package org.example.libmgmt.ui.components.header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.components.body.contentSection.PDFViewer;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * Controller for PDF viewer.
 */
public class PDFController implements HeaderController {
  private final PDFViewer viewer;
  private final BorderPane container;
  private Map<String, ImageView> icon;

  private HBox pageManipulator;
  private Button zoomIn;
  private Button zoomOut;
  private Button zoomToFit;
  private boolean widthFitted;
  private ComboBox<String> scaleLevelBox;
  private final double[] scaleLevel = {
      0.25, 0.33, 0.5, 0.67,
      0.8, 0.9, 1, 1.1, 1.25,
      1.50, 1.75, 2, 2.5,
      3, 3.5, 4, 4.5, 5};
  private int currentScaleLevel;
  private TextField currentPage;
  private Text totalPage;

//  private HBox searchGroup;
//  private Button prevMatch;
//  private Button nextMatch;
//  private Text totalMatch;
//  private TextField searchBox;

  private HBox bookmarkGroup;
  private Button bookmarkList;
  private Button bookmark;

  /**
   * Constructor.
   *
   * @param viewer Target viewer.
   */
  public PDFController(PDFViewer viewer) {
    currentScaleLevel = 6;
    widthFitted = false;
    this.viewer = viewer;
    createPageManipulator();
    createBookmarkGroup();
    container = new BorderPane();
    container.setCenter(pageManipulator);
    container.setLeft(bookmarkGroup);
//    createSearchGroup();
//    container.setRight(searchGroup);
    loadMedia();
    setFunction();
    style();
  }

  private void loadMedia() {
    icon = new HashMap<>();
    icon.put("zoom_in", null);
    icon.put("zoom_out", null);
    icon.put("fit_to_width", null);
    icon.put("fit_to_height", null);
    icon.put("up_arrow", null);
    icon.put("down_arrow", null);
    icon.put("bookmark", null);
    icon.put("bookmark_list", null);
    String dir = "img/pdfcontroller/";
    icon.replaceAll((k, v) -> new ImageView(new Image(
        Objects.requireNonNull(LibMgmt.class.getResourceAsStream(dir + k + ".png")))));
    zoomIn.setGraphic(icon.get("zoom_in"));
    zoomOut.setGraphic(icon.get("zoom_out"));
    zoomToFit.setGraphic(icon.get("fit_to_width"));
//    prevMatch.setGraphic(icon.get("up_arrow"));
//    nextMatch.setGraphic(icon.get("down_arrow"));
    bookmark.setGraphic(icon.get("bookmark"));
    bookmarkList.setGraphic(icon.get("bookmark_list"));
  }

  private void createBookmarkGroup() {
    bookmarkList = new Button();
    bookmarkList.setTooltip(new Tooltip("Danh sách dấu trang"));
    bookmark = new Button();
    bookmark.setTooltip(new Tooltip("Đánh dấu trang"));
    bookmarkGroup = new HBox(bookmarkList, bookmark);
  }

//  private void createSearchGroup() {
//    prevMatch = new Button();
//    prevMatch.setTooltip(new Tooltip("Kết quả trước"));
//    nextMatch = new Button();
//    nextMatch.setTooltip(new Tooltip("Kết quả tiếp"));
//    searchBox = new TextField();
//    totalMatch = new Text();
//    searchGroup = new HBox(totalMatch, prevMatch, nextMatch, searchBox);
//  }

  private void createPageManipulator() {
    zoomIn = new Button();
    zoomIn.setTooltip(new Tooltip("Phóng to"));
    zoomOut = new Button();
    zoomOut.setTooltip(new Tooltip("Thu nhỏ"));
    zoomToFit = new Button();
    zoomToFit.setTooltip(new Tooltip("Vừa bề ngang"));
    scaleLevelBox = new ComboBox<>();
    for (double d : scaleLevel) {
      scaleLevelBox.getItems().add(Math.round(d * 100) + "%");
    }
    scaleLevelBox.getSelectionModel().select("100%");
    currentPage = new TextField();
    totalPage = new Text();
    Task<Void> loadPageNumber = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        while (viewer.getTotalPage() == -1) {
          Thread.sleep(500);
        }
        Platform.runLater(() -> totalPage.setText("trên " + viewer.getTotalPage()));
        return null;
      }
    };
    Thread t = new Thread(loadPageNumber);
    t.setDaemon(true);
    t.start();
    pageManipulator = new HBox(zoomOut, zoomIn, zoomToFit, scaleLevelBox, currentPage, totalPage);
  }

  @Override
  public void setFunction() {
    zoomIn.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY) {
        totalPage.setText("trên " + viewer.getTotalPage());
      }
    });
    viewer.getDocViewer().vvalueProperty().addListener((observable, oldValue, newValue) -> {
      String t = Integer.toString(viewer.getCurrentPage());
      currentPage.setText(t);
    });
    currentPage.setOnKeyPressed(key -> {
      if (key.getCode() == KeyCode.ENTER) {
        try {
          viewer.switchToPage(Integer.parseInt(currentPage.getText()));
        } catch (NumberFormatException e) {
          currentPage.setText(Integer.toString(viewer.getCurrentPage()));
        }
      }
    });
    currentPage.focusedProperty().addListener((obs, ov, nv) -> {
      if (!nv) {
        currentPage.setText(Integer.toString(viewer.getCurrentPage()));
      }
    });
    zoomIn.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY) {
        if (widthFitted) {
          widthFitted = false;
          zoomToFit.setGraphic(icon.get("fit_to_width"));
          zoomToFit.setTooltip(new Tooltip("Vừa bề ngang"));
        }
        currentScaleLevel += 1;
        currentScaleLevel = Math.min(17, currentScaleLevel);
        scaleLevelBox.getSelectionModel().select(currentScaleLevel);
      }
    });
    zoomOut.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY) {
        if (widthFitted) {
          widthFitted = false;
          zoomToFit.setGraphic(icon.get("fit_to_width"));
          zoomToFit.setTooltip(new Tooltip("Vừa bề ngang"));
        }
        currentScaleLevel -= 1;
        currentScaleLevel = Math.max(0, currentScaleLevel);
        scaleLevelBox.getSelectionModel().select(currentScaleLevel);
      }
    });
    scaleLevelBox.getSelectionModel().selectedIndexProperty()
        .addListener((obs, oldVal, newVal) -> {
          viewer.scaleViewport(scaleLevel[(int) newVal]);
          currentScaleLevel = (int) newVal;
        });
    zoomToFit.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY) {
        double scaling = viewer.getScalingLevelToFit(widthFitted);
        for (int i = 0; i < 18; i++) {
          if (i == 17 || scaleLevel[i + 1] > scaling) {
            currentScaleLevel = i;
            break;
          }
        }
        if (widthFitted) {
          zoomToFit.setGraphic(icon.get("fit_to_height"));
          zoomToFit.setTooltip(new Tooltip("Vừa bề rộng"));
        } else {
          zoomToFit.setGraphic(icon.get("fit_to_width"));
          zoomToFit.setTooltip(new Tooltip("Vừa bề ngang"));
        }
        System.out.println(scaling);
        widthFitted = !widthFitted;
        scaleLevelBox.getSelectionModel().select(currentScaleLevel);
      }
    });

    bookmark.setOnMouseClicked(e -> viewer.addBookmark());

    bookmarkList.setOnMouseClicked(e -> viewer.toggleBookmarkList());
  }

  @Override
  public void style() {
    Style.styleRoundedSolidButton(zoomIn, 30, 30, 16);
    Style.styleRoundedSolidButton(zoomOut, 30, 30, 16);
    Style.styleRoundedSolidButton(zoomToFit, 30, 30, 16);
    Style.styleRoundedSolidButton(bookmark, 30, 30, 16);
    Style.styleRoundedSolidButton(bookmarkList, 30, 30, 16);
    Style.styleText(totalPage, 16);
    StyleForm.styleComboBox(scaleLevelBox, 100, 30, 16, "");
    Style.styleTextField(currentPage, 50, 30, 16, true, "");
    pageManipulator.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    bookmarkGroup.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    pageManipulator.setAlignment(Pos.CENTER);
    bookmarkGroup.setAlignment(Pos.CENTER);
    BorderPane.setAlignment(pageManipulator, Pos.CENTER);
    BorderPane.setAlignment(bookmarkGroup, Pos.CENTER_LEFT);
    pageManipulator.setSpacing(5);
//    Style.styleRoundedSolidButton(prevMatch, 30, 30, 16);
//    Style.styleRoundedSolidButton(nextMatch, 30, 30, 16);
//    Style.styleText(totalMatch, 16);
//    Style.styleTextField(searchBox, 200, 30, 16, true, "Tìm trong tài liệu");
//    searchGroup.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
//    searchGroup.setAlignment(Pos.CENTER);
//    BorderPane.setAlignment(searchGroup, Pos.CENTER_RIGHT);
  }

  @Override
  public Region getLayout() {
    return container;
  }
}
