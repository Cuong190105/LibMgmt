package org.example.libmgmt.ui.components.body;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.DocumentDAO;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.DB.UserDAO;
import org.example.libmgmt.ui.components.body.contentSection.DocumentLibrary;
import org.example.libmgmt.ui.components.body.contentSection.MemberListView;

import java.util.List;

public class MemberSearchPanel extends SearchPanel {
    public static final int VIEW_ALL = 0;
    public static final int VIEW_LIBRARIAN = 1;
    public static final int VIEW_READER = 2;
    public static final int SORTBY_ID = 0;
    public static final int SORTBY_NAME = 1;
    public static final int SORT_LARGEST_FIRST = 4;
    private int currentViewOption;
    private int currentSortOption;
    private MemberListView member;

    public MemberSearchPanel(MemberListView member) {
        this.member = member;
        currentSortOption = SORTBY_ID;
        currentViewOption = VIEW_ALL;
        setFunction();
        addViewOption();
        addSortOption();
    }

    @Override
    public void setFunction() {
        searchBox.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                searchBox.setDisable(true);
                member.toggleLoadingRing(true);
                Task<Void> searchDoc = new Task<>() {
                    @Override
                    protected Void call() {
                        try {
                            List<User> result = UserDAO.getInstance().searchUser(searchBox.getText());
                            member.loadSearchResults(1, result);
                        } catch (Exception e) {
                            member.loadSearchResults(-1, null);
                        }
                        Platform.runLater(() -> {
                            member.showSearchResults(currentViewOption, currentSortOption);
                            searchBox.setDisable(false);
                        });
                        return null;
                    }
                };
                new Thread(searchDoc).start();
            }
        });

        viewOption.setOnAction(_ -> {
            currentViewOption = viewOption.getItems().indexOf(viewOption.getValue());
            member.showSearchResults(currentViewOption, currentSortOption);
        });

        sortingOption.setOnAction(_ -> {
            int selection = sortingOption.getItems().indexOf(sortingOption.getValue());
            currentSortOption = selection / 2 + ((selection) % 2) * SORT_LARGEST_FIRST;
        });
    }

    @Override
    public void addViewOption() {
        viewOption.setItems(FXCollections.observableArrayList(
                "Tất cả", "Thủ thư", "Người đọc"
        ));
    }

    @Override
    public void addSortOption() {
        sortingOption.getItems().addAll(
                "Mã số - Tăng dần", "Mã số - Giảm dần",
                "Tên - A đến Z", "Tên - Z đến A"
        );
    }
}
