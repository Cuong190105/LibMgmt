package org.example.libmgmt.ui.components.body.contentSection;

import java.util.Comparator;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.libmgmt.AppLogger;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.components.body.DocumentSearchPanel;
import org.example.libmgmt.ui.components.body.LoadingRing;
import org.example.libmgmt.ui.components.body.ResultPageSwitch;
import org.example.libmgmt.ui.components.body.card.DocumentCard;
import org.example.libmgmt.ui.style.Style;

/**
 * Creates a gallery view to show document list and search results.
 */
public class DocumentLibrary {
  private int loadStatus;
  private static final Label getStarted = new Label(
      "Tìm kiếm tài liệu bạn cần bằng tên, mã số, hoặc ISBN."
  );
  private static final Label noResult = new Label(
      "Không có kết quả."
  );
  private static final Label error = new Label(
      "Có lỗi xảy ra. Vui lòng thử lại sau"
  );
  private List<Document> results;
  private int page;
  private int totalPage;
  private final Label numberOfResultsLabel;
  private final TilePane resultList;
  private final VBox contentWrapper;
  private final ScrollPane wrapper;
  private Button addDocument;
  private final AnchorPane container;

  /**
   * Constructor.
   */
  public DocumentLibrary() {
    loadStatus = 0;
    page = 0;
    container = new AnchorPane();
    resultList = new TilePane();
    numberOfResultsLabel = new Label();
    contentWrapper = new VBox(resultList);
    wrapper = new ScrollPane(contentWrapper);
    if (UserControl.getUser().isLibrarian()) {
      addDocument = new Button("Thêm tài liệu");
      container.getChildren().addAll(wrapper, addDocument);
    } else {
      container.getChildren().add(getStarted);
    }
    setFunction();
    style();
  }

  private void setFunction() {
    if (UserControl.getUser().isLibrarian()) {
      addDocument.setOnMouseClicked(_ -> {
        UIHandler.openAddDocumentPanel();
      });
    }
  }

  private void style() {
    resultList.setVgap(25);
    resultList.setHgap(25);
    IntegerBinding numberOfNodes = Bindings.createIntegerBinding(
        () -> (int) (contentWrapper.widthProperty().get() + 25) / 275,
        contentWrapper.widthProperty()
    );

    ObjectBinding<Insets> padding = Bindings.createObjectBinding(
        () -> {
          double paddingValue = ((contentWrapper.getWidth() + 20
              - 275 * numberOfNodes.get()) / 2);
          return new Insets(10, paddingValue, 0, paddingValue);
        }, contentWrapper.widthProperty()
    );

    resultList.paddingProperty().bind(padding);
    Style.styleTitle(getStarted, 20);
    Style.styleTitle(noResult, 20);
    Style.styleTitle(error, 20);
    Style.styleTitle(numberOfResultsLabel, 16);
    contentWrapper.setSpacing(10);
    wrapper.setFitToWidth(true);

    contentWrapper.minHeightProperty().bind(wrapper.heightProperty().subtract(20));
    contentWrapper.prefWidthProperty().bind(wrapper.widthProperty().subtract(50));
    contentWrapper.setAlignment(Pos.TOP_CENTER);

    if (addDocument != null) {
      AnchorPane.setBottomAnchor(addDocument, 0.0);
      AnchorPane.setLeftAnchor(addDocument, 0.0);
      AnchorPane.setRightAnchor(addDocument, 0.0);
    }

    AnchorPane.setTopAnchor(wrapper, 0.0);
    if (addDocument != null) {
      AnchorPane.setBottomAnchor(wrapper, 60.0);
    } else {
      AnchorPane.setBottomAnchor(wrapper, 0.0);
    }
    AnchorPane.setLeftAnchor(wrapper, 0.0);
    AnchorPane.setRightAnchor(wrapper, 0.0);

    if (UserControl.getUser().isLibrarian()) {
      Style.styleRoundedButton(addDocument, Style.LIGHTGREEN, 250, 50, 20);
    }
  }

  /**
   * Loads search results to display.
   * @param status Search status indicato used for handling different search scenarios.
   * @param results The result data set
   * @param viewOption A sesult filter, value set with constants in DocumentSearchPanel class.
   * @param sortOption Sorting option indicator, value set with constants.
   */
  public void loadSearchResults(int status, List<Document> results,
                                int viewOption, int sortOption) {
    page = 0;
    this.results = results;
    this.loadStatus = status;
    if (results != null) {
      sortResults(sortOption);
      applyFilter(viewOption);
      totalPage = results.size() / 20 + (results.size() % 20  == 0 ? 0 : 1);
    }
  }

  private void changePage(int change) {
    toggleLoadingRing(true);
    page += change;
    showSearchResults();
  }

  /**
   * Displays search result on screen.
   */
  public void showSearchResults() {
    wrapper.setVvalue(0);
    contentWrapper.getChildren().clear();
    resultList.getChildren().clear();
    if (results != null) {
      for (int i = page * 20; i < Math.min((page + 1) * 20, results.size()); i++) {
        resultList.getChildren().add(new DocumentCard(results.get(i)).getCard());
      }
      if (!results.isEmpty()) {
        ResultPageSwitch sw = new ResultPageSwitch(page == totalPage - 1, page == 0,
            "Trang " + (page + 1) + "/" + (totalPage),
            () -> {
              changePage(1);
              return null;
            },
            () -> {
              changePage(-1);
              return null;
            });
        numberOfResultsLabel.setText("Hiển thị kết quả "
            + (page * 20 + 1) + "-" + (Math.min((page + 1) * 20, results.size()))
            + " trên " + results.size() + ".");
        contentWrapper.getChildren().addAll(numberOfResultsLabel, resultList, sw.getSwitch());
      }
    }
    toggleLoadingRing(false);
  }

  private void sortResults(int option) {
    if (results == null) {
      return;
    }
    Comparator<Document> cmp = (o1, o2) -> {
      int tmp = 0;
      switch (option & (DocumentSearchPanel.SORT_LARGEST_FIRST - 1)) {
        case DocumentSearchPanel.SORTBY_ID -> tmp = o1.getDocID() - o2.getDocID();
        case DocumentSearchPanel.SORTBY_PUBLISHYEAR -> tmp = o1.getPublishYear() - o2.getPublishYear();
        case DocumentSearchPanel.SORTBY_NAME -> tmp = o1.getTitle().compareTo(o2.getTitle());
        default ->
            AppLogger.log(new IllegalStateException(
                "Unexpected value: " + (option & (DocumentSearchPanel.SORT_LARGEST_FIRST - 1))));
      }
      if ((option & DocumentSearchPanel.SORT_LARGEST_FIRST)
          == DocumentSearchPanel.SORT_LARGEST_FIRST) {
        tmp *= -1;
      }
      return tmp;
    };
    results.sort(cmp);
  }

  private void applyFilter(int option) {
    if (results == null) {
      return;
    }
    switch (option) {
      case DocumentSearchPanel.VIEW_BOOK -> {
        results.removeIf(d -> d.isThesis());
      }
      case DocumentSearchPanel.VIEW_THESIS -> {
        results.removeIf(d -> !d.isThesis());
      }
    }
  }

  public AnchorPane getContent() {
    return container;
  }

  /**
   * Display loading animation.
   *
   * @param show Shows/Hides animation.
   */
  public void toggleLoadingRing(boolean show) {
    AppLogger.log("Triggered with " + show);
    FadeTransition fadeOut = new FadeTransition(Duration.millis(100), container);
    FadeTransition fadeIn = new FadeTransition(Duration.millis(100), container);
    fadeOut.setOnFinished(_ -> {
      container.getChildren().clear();
      if (show) {
        Pane ring = new LoadingRing().getRing();
        StackPane tmp = new StackPane(ring);
        container.getChildren().add(tmp);
        AnchorPane.setTopAnchor(tmp, 0.0);
        AnchorPane.setLeftAnchor(tmp, 0.0);
        AnchorPane.setBottomAnchor(tmp, 0.0);
        AnchorPane.setRightAnchor(tmp, 0.0);
      } else {
        if (loadStatus == -1) {
          contentWrapper.getChildren().add(error);
        } else if (loadStatus == 0) {
          contentWrapper.getChildren().add(getStarted);
        } else {
          if (results.isEmpty()) {
            contentWrapper.getChildren().add(noResult);
          }
        }
        container.getChildren().add(wrapper);
        if (addDocument != null) {
          container.getChildren().add(addDocument);
        }
      }
    });
    fadeOut.setToValue(0);
    fadeIn.setToValue(1);
    SequentialTransition t = new SequentialTransition(fadeOut, fadeIn);
    t.play();
  }
}
