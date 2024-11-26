package org.example.libmgmt.ui.components.body;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.DocumentDAO;
import org.example.libmgmt.ui.components.body.contentSection.DocumentLibrary;

import java.util.List;

public class DocumentSearchPanel extends SearchPanel {
    public static final int VIEW_ALL = 0;
    public static final int VIEW_BOOK = 1;
    public static final int VIEW_THESIS = 2;
    public static final int SORTBY_ID = 0;
    public static final int SORTBY_NAME = 1;
    public static final int SORTBY_PUBLISHYEAR = 2;
    public static final int SORT_LARGEST_FIRST = 4;
    private int currentViewOption;
    private int currentSortOption;
    private DocumentLibrary library;

    public DocumentSearchPanel(DocumentLibrary library) {
        this.library = library;
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
                library.toggleLoadingRing(true);
                Task<Void> searchDoc = new Task<>() {
                    @Override
                    protected Void call() {
                        try {
                            List<Document> result = DocumentDAO.getInstance().searchDocKey(searchBox.getText());
                            library.loadSearchResults(1, result);
                        } catch (Exception e) {
                            library.loadSearchResults(-1, null);
                        }
                        Platform.runLater(() -> {
                            library.showSearchResults(currentViewOption, currentSortOption);
                            searchBox.setDisable(false);
                        });
                        return null;
                    }
                };
                new Thread(searchDoc).start();
            }
        });

        viewOption.setOnAction(_ -> {
            library.toggleLoadingRing(true);
            currentViewOption = viewOption.getItems().indexOf(viewOption.getValue());
            library.showSearchResults(currentViewOption, currentSortOption);
        });

        sortingOption.setOnAction(_ -> {
            library.toggleLoadingRing(true);
            int selection = sortingOption.getItems().indexOf(sortingOption.getValue());
            currentSortOption = selection / 2 + (selection % 2) * SORT_LARGEST_FIRST;
            library.showSearchResults(currentViewOption, currentSortOption);
        });
    }

    @Override
    public void addViewOption() {
        viewOption.setItems(FXCollections.observableArrayList(
                "Tất cả", "Sách", "Luận án"
        ));
    }

    @Override
    public void addSortOption() {
        sortingOption.getItems().addAll(
                "Mã số - Tăng dần", "Mã số - Giảm dần",
                "Tên - A đến Z", "Tên - Z đến A",
                "Năm xuất bản - Cũ nhất", "Năm xuât bản - Mới nhất"
        );
    }
}
