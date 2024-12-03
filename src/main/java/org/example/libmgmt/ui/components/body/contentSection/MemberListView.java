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
import org.example.libmgmt.DB.User;
import org.example.libmgmt.ui.components.body.LoadingRing;
import org.example.libmgmt.ui.components.body.searchPanel.MemberSearchPanel;
import org.example.libmgmt.ui.components.body.ResultPageSwitch;
import org.example.libmgmt.ui.components.body.card.MemberCard;
import org.example.libmgmt.ui.style.Style;

/**
 * A Gallery view displaying member list and member search result.
 */
public class MemberListView {
  private int loadStatus;
  private static final Label noResult = new Label(
      "Không có kết quả."
  );
  private static final Label error = new Label(
      "Có lỗi xảy ra. Vui lòng thử lại sau"
  );
  private List<User> results;
  private final Label numberOfResultsLabel;
  private final TilePane resultList;
  private final VBox contentWrapper;
  private final ScrollPane wrapper;
  private final Button addMember;
  private final AnchorPane container;
  private int page;
  private int totalPage;

  /**
   * Creates panel.
   */
  public MemberListView() {
    loadStatus = 0;
    container = new AnchorPane();
    resultList = new TilePane();
    numberOfResultsLabel = new Label();
    contentWrapper = new VBox(resultList);
    wrapper = new ScrollPane(contentWrapper);
    addMember = new Button("Thêm thành viên");
    container.getChildren().addAll(wrapper, addMember);
    setFunction();
    style();
  }

  private void setFunction() {
    addMember.setOnMouseClicked(_ -> {
//            UIHandler.switchToAddUser();
    });
  }

  private void style() {
    resultList.setVgap(25);
    resultList.setHgap(25);
    Style.styleTitle(noResult, 20);
    Style.styleTitle(error, 20);
    Style.styleTitle(numberOfResultsLabel, 16);
    contentWrapper.setSpacing(10);
    wrapper.setFitToWidth(true);

    IntegerBinding numberOfNodes = Bindings.createIntegerBinding(
        () -> (int) (wrapper.widthProperty().get() - 25) / 425,
        wrapper.widthProperty()
    );

    ObjectBinding<Insets> padding = Bindings.createObjectBinding(
        () -> {
          int paddingValue = (int) ((wrapper.getWidth() - 25
              - 425 * numberOfNodes.get()) / 2);
          paddingValue = Math.max(0, paddingValue);
          return new Insets(10, paddingValue, 0, paddingValue);
        }, wrapper.widthProperty()
    );

    resultList.paddingProperty().bind(padding);

    contentWrapper.minHeightProperty().bind(wrapper.heightProperty().subtract(10));
    contentWrapper.prefWidthProperty().bind(wrapper.widthProperty().subtract(10));
    contentWrapper.setAlignment(Pos.TOP_CENTER);

    AnchorPane.setBottomAnchor(addMember, 0.0);
    AnchorPane.setLeftAnchor(addMember, 0.0);
    AnchorPane.setRightAnchor(addMember, 0.0);

    AnchorPane.setTopAnchor(wrapper, 0.0);
    AnchorPane.setBottomAnchor(wrapper, 60.0);
    AnchorPane.setLeftAnchor(wrapper, 0.0);
    AnchorPane.setRightAnchor(wrapper, 0.0);

    Style.styleRoundedSolidButton(addMember, Style.LIGHTGREEN, 250, 50, 20);
  }

  /**
   * Loads search results to display.
   *
   * @param status Search status indicator used to handle many search scenarios properly.
   * @param results Result dataset.
   */
  public void loadSearchResults(int status, List<User> results) {
    page = 0;
    this.results = results;
    this.loadStatus = status;
    if (results != null) {
      totalPage = results.size() / 20 + (results.size() % 20  == 0 ? 0 : 1);
    }
  }

  private void changePage(int change) {
    toggleLoadingRing(true);
    page += change;
    showSearchResults();
  }

  /**
   * Displays search result to screen.
   */
  public void showSearchResults() {
    contentWrapper.getChildren().clear();
    resultList.getChildren().clear();
    System.gc();
    if (results != null) {
      for (int i = page * 20; i < Math.min((page + 1) * 20, results.size()); i++) {
        resultList.getChildren().add(new MemberCard(results.get(i)).getCard());
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

  /**
   * Sort the result data set.
   *
   * @param option Sort option. Use constant value in MemberSearchPanel class to set option.
   */
  public void sortResults(int option) {
    if (results == null) {
      return;
    }
    Comparator<User> cmp = (o1, o2) -> {
      int tmp = 0;
      switch (option & (MemberSearchPanel.SORT_LARGEST_FIRST - 1)) {
        case MemberSearchPanel.SORTBY_ID -> tmp = o1.getUid() - o2.getUid();
        case MemberSearchPanel.SORTBY_NAME -> tmp = o1.getName().compareTo(o2.getName());
        default ->
            AppLogger.log(new IllegalStateException(
                "Unexpected value: " + (option & (MemberSearchPanel.SORT_LARGEST_FIRST - 1))));
      }
      if ((option & MemberSearchPanel.SORT_LARGEST_FIRST)
          == MemberSearchPanel.SORT_LARGEST_FIRST) {
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
      case MemberSearchPanel.VIEW_LIBRARIAN -> {
        results.removeIf(d -> d.isLibrarian());
      }
      case MemberSearchPanel.VIEW_READER -> {
        results.removeIf(d -> !d.isLibrarian());
      }
    }
  }

  public AnchorPane getContent() {
    return container;
  }

  /**
   * Turn on or off loading animation.
   *
   * @param show Shows/Hides animation.
   */
  public void toggleLoadingRing(boolean show) {
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
        } else {
          if (results.isEmpty()) {
            contentWrapper.getChildren().add(noResult);
          }
        }
        container.getChildren().addAll(wrapper, addMember);
      }
    });
    fadeOut.setToValue(0);
    fadeIn.setToValue(1);
    SequentialTransition t = new SequentialTransition(fadeOut, fadeIn);
    t.play();
  }
}
