package org.example.libmgmt.ui.components.body.contentSection;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.components.body.MemberSearchPanel;
import org.example.libmgmt.ui.components.body.LoadingRing;
import org.example.libmgmt.ui.components.body.card.MemberCard;
import org.example.libmgmt.ui.style.Style;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MemberListView {
    private int loadStatus;
    private static final Label noResult = new Label(
            "Không có kết quả."
    );
    private static final Label error = new Label(
            "Có lỗi xảy ra. Vui lòng thử lại sau"
    );
    private List<User> results;
    private int numberOfResults;
    private Label numberOfResultsLabel;
    private TilePane resultList;
    private VBox contentWrapper;
    private ScrollPane wrapper;
    private Button addMember;
    private Button clearSearch;
    private HBox btnWrapper;
    private AnchorPane container;

    public MemberListView() {
        loadStatus = 0;
        container = new AnchorPane();
        resultList = new TilePane();
        numberOfResultsLabel = new Label();
        btnWrapper = new HBox();
        contentWrapper = new VBox(resultList);
        wrapper = new ScrollPane(contentWrapper);
//            getLatestDocument()
        addMember = new Button("Thêm thành viên");
        clearSearch = new Button("Xoá tìm kiếm");
        btnWrapper.getChildren().add(addMember);
        container.getChildren().addAll(wrapper, btnWrapper);
        setFunction();
        style();
    }

    private void setFunction() {
        addMember.setOnMouseClicked(_ -> {
//            UIHandler.switchToAddUser();
        });

        clearSearch.setOnMouseClicked(_ -> {
            //getLatestUser()
            btnWrapper.getChildren().remove(1);
        });

//        wrapper.setOnScroll(_ -> {
//            if (lazyLoadTrigger && wrapper.getHvalue() >= wrapper.getHmax() * 0.8) {
//                //LoadMore
//            }
//        });
    }

    private void style() {
        resultList.setVgap(25);
        resultList.setHgap(25);
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
            Style.styleRoundedButton(addMember, Style.LIGHTGREEN, 250, 50, 20);
            Style.styleRoundedButton(clearSearch, Style.LIGHTGREEN, 250, 50, 20);
            btnWrapper.setSpacing(50);
        }
        btnWrapper.setAlignment(Pos.CENTER);
    }

    public void loadSearchResults(int status, List<User> results) {
        this.results = results;
        this.loadStatus = status;
    }

    public void showSearchResults(int viewOption, int sortOption) {
        resultList.getChildren().clear();
        sortResults(sortOption);
        applyFilter(viewOption);
        toggleLoadingRing(false);
    }

    private void sortResults(int option) {
        if (results == null) {
            return;
        }
        Comparator<User> cmp = (o1, o2) -> {
            int tmp = 0;
            switch (option & (MemberSearchPanel.SORT_LARGEST_FIRST - 1)) {
                case MemberSearchPanel.SORTBY_ID -> tmp = o1.getUID() - o2.getUID();
                case MemberSearchPanel.SORTBY_NAME -> tmp = o1.getName().compareTo(o2.getName());
            }
            if ((option & MemberSearchPanel.SORT_LARGEST_FIRST)
                    == MemberSearchPanel.SORT_LARGEST_FIRST) {
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
        resultList.getChildren().clear();
        for (User d : results) {
            switch (option) {
                case MemberSearchPanel.VIEW_ALL -> {
                    resultList.getChildren().add(new MemberCard(d).getCard());
                }
                case MemberSearchPanel.VIEW_LIBRARIAN -> {
                    if (d.isLibrarian()) {
                        resultList.getChildren().add(new MemberCard(d).getCard());
                    }
                }
                case MemberSearchPanel.VIEW_READER -> {
                    if (!d.isLibrarian()) {
                        resultList.getChildren().add(new MemberCard(d).getCard());
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
