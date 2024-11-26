package org.example.libmgmt.ui.components.body.contentSection;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.components.body.DocumentSearchPanel;
import org.example.libmgmt.ui.components.body.LoadingRing;
import org.example.libmgmt.ui.components.body.card.DocumentCard;
import org.example.libmgmt.ui.style.Style;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private int numberOfResults;
    private Label numberOfResultsLabel;
    private TilePane resultList;
    private VBox contentWrapper;
    private ScrollPane wrapper;
    private HBox btnWrapper;
    private Button addDocument;
    private Button clearSearch;
    private AnchorPane container;

    public DocumentLibrary() {
        loadStatus = 0;
        container = new AnchorPane();
        resultList = new TilePane();
        numberOfResultsLabel = new Label();
        btnWrapper = new HBox();
        contentWrapper = new VBox(resultList);
        wrapper = new ScrollPane(contentWrapper);
        if (UserControl.getUser().isLibrarian()) {
//            getLatestDocument()
            addDocument = new Button("Thêm tài liệu");
            clearSearch = new Button("Xoá tìm kiếm");
            btnWrapper.getChildren().add(addDocument);
            container.getChildren().addAll(wrapper, btnWrapper);
        } else {
            container.getChildren().add(getStarted);
        }
        setFunction();
        style();
    }

    private void setFunction() {
        if (UserControl.getUser().isLibrarian()) {
            addDocument.setOnMouseClicked(_ -> {
//            UIHandler.switchToAddDocument();
            });

            clearSearch.setOnMouseClicked(_ -> {
                //getLatestDocument()
                btnWrapper.getChildren().remove(1);
            });
        }

//        wrapper.setOnScroll(_ -> {
//            if (lazyLoadTrigger && wrapper.getHvalue() >= wrapper.getHmax() * 0.8) {
//                //LoadMore
//            }
//        });
    }

    private void style() {
        resultList.setVgap(25);
        resultList.setHgap(25);
        Style.styleTitle(getStarted, 20);
        Style.styleTitle(noResult, 20);
        Style.styleTitle(error, 20);
        Style.styleTitle(numberOfResultsLabel, 12);
        contentWrapper.setSpacing(10);
        wrapper.setFitToWidth(true);

        contentWrapper.minHeightProperty().bind(wrapper.heightProperty().subtract(10));
        contentWrapper.prefWidthProperty().bind(container.widthProperty());
        contentWrapper.setAlignment(Pos.CENTER);

        AnchorPane.setBottomAnchor(btnWrapper, 0.0);
        AnchorPane.setLeftAnchor(btnWrapper, 0.0);
        AnchorPane.setRightAnchor(btnWrapper, 0.0);

        AnchorPane.setTopAnchor(wrapper, 0.0);
        AnchorPane.setBottomAnchor(wrapper, 60.0);
        AnchorPane.setLeftAnchor(wrapper, 0.0);
        AnchorPane.setRightAnchor(wrapper, 0.0);

        if (UserControl.getUser().isLibrarian()) {
            Style.styleRoundedButton(addDocument, Style.LIGHTGREEN, 250, 50, 20);
            Style.styleRoundedButton(clearSearch, Style.LIGHTGREEN, 250, 50, 20);
            btnWrapper.setSpacing(50);
        }
        btnWrapper.setAlignment(Pos.CENTER);
    }

    public void loadSearchResults(int status, List<Document> results) {
        this.results = results;
        this.loadStatus = status;
    }

    public void showSearchResults(int viewOption, int sortOption) {
        resultList.getChildren().clear();
        contentWrapper.getChildren().clear();
        sortResults(sortOption);
        applyFilter(viewOption);
        if (numberOfResults != 0) {
            numberOfResultsLabel.setText("Tìm thấy " + numberOfResults + " kết quả.");
            contentWrapper.getChildren().addAll(numberOfResultsLabel, resultList);
        }
        if (loadStatus != 0 && btnWrapper.getChildren().size() == 1) {
            btnWrapper.getChildren().add(clearSearch);
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
            }
            if ((option & DocumentSearchPanel.SORT_LARGEST_FIRST)
                    == DocumentSearchPanel.SORT_LARGEST_FIRST) {
                tmp *= -1;
            }
            return tmp;
        };
        Collections.sort(results, cmp);
    }

    private void applyFilter(int option) {
        if (results == null) {
            return;
        }
        numberOfResults = 0;
        resultList.getChildren().clear();
        for (Document d : results) {
            switch (option) {
                case DocumentSearchPanel.VIEW_ALL -> {
                    numberOfResults++;
                    resultList.getChildren().add(new DocumentCard(d).getCard());
                }
                case DocumentSearchPanel.VIEW_BOOK -> {
                    if (!d.isThesis()) {
                        numberOfResults++;
                        resultList.getChildren().add(new DocumentCard(d).getCard());
                    }
                }
                case DocumentSearchPanel.VIEW_THESIS -> {
                    if (d.isThesis()) {
                        numberOfResults++;
                        resultList.getChildren().add(new DocumentCard(d).getCard());
                    }
                }
            }
        }
    }

    public AnchorPane getContent() {
        return container;
    }

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
                    if (numberOfResults == 0) {
                        contentWrapper.getChildren().add(noResult);
                    }
                }
                container.getChildren().addAll(wrapper, btnWrapper);
            }
        });
        fadeOut.setToValue(0);
        fadeIn.setToValue(1);
        SequentialTransition t = new SequentialTransition(fadeOut, fadeIn);
        t.play();
    }
}
